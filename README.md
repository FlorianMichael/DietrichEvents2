# DietrichEvents2
One of the fastest Event libraries for Java in the world

## Contact
If you encounter any issues, please report them on the
[issue tracker](https://github.com/FlorianMichael/DietrichEvents2/issues).  
If you just want to talk or need help with DietrichEvents2 feel free to join my
[Discord](https://discord.gg/BwWhCHUKDf).

## How to add this to your project
Just copy this part to your *build.gradle*:
```groovy
repositories {
    maven {
        name = "Jitpack"
        url = "https://jitpack.io"
    }
}

dependencies {
    implementation "com.github.FlorianMichael:DietrichEvents2:1.0.0"
}
```

## Example usage
### Create instance
You can use either **new DietrichEvents2(exception -> {});** or **DietrichEvents2.global()** to access an instance of the EventSystem,

### Create an Event
```java
public interface ExampleListener extends Listener {

    void onTest(final String example);

    class ExampleEvent extends AbstractEvent<ExampleListener> {

        /**
         * The ID has to be incremented for every new Event
         */
        public final static int ID = 0;
        public final String example;

        public ExampleEvent(final String example) {
            this.example = example;
        }

        @Override
        public void call(ExampleListener listener) {
            listener.onTest(example);
        }
    }
}
```

### Register Listener
```java
public class Test implements ExampleListener {
    
    public void begin() {
        DietrichEvents2.global().subscribe(ExampleEvent.ID, this);
    }
    
    @Override
    public void onTest(String example) {
        System.out.println("Executed once!");
        DietrichEvents2.global().unsubscribe(ExampleEvent.ID, this);
    }
}
```

### Calling an Event
````java
// There is also a post() method which uses the error handler (try catch)
DietrichEvents2.global().postInternal(new ExampleEvent("Hello World!"));
````

## JMH Benchmark
The Benchmark shows the average time it takes to call an event 100.000 times.

| Benchmark                             | Mode | Cnt | Score      | Error      | Units |
|---------------------------------------|------|-----|------------|------------|-------|
| BenchmarkCaller.callBenchmarkListener | avgt | 4   | 315071,130 | 189479,490 | ns/op |
