# DietrichEvents2
One of the fastest Java event systems in the world, which still has a lot of features

## Contact
If you encounter any issues, please report them on the
[issue tracker](https://github.com/FlorianMichael/DietrichEvents2/issues).  
If you just want to talk or need help with DietrichEvents2 feel free to join my
[Discord](https://discord.gg/BwWhCHUKDf).

## How to add this to your project
### Gradle/Maven
To use DietrichEvents2 with Gradle/Maven you can use this [Maven server](https://maven.lenni0451.net/#/releases/de/florianmichael/DietrichEvents2) or [Jitpack](https://jitpack.io/#FlorianMichael/DietrichEvents2).  
You can also find instructions how to implement it into your build script there.

### Jar File
If you just want the latest jar file you can download it from the GitHub [Actions](https://github.com/FlorianMichael/DietrichEvents2/actions) or use the [Release](https://github.com/FlorianMichael/DietrichEvents2/releases).

## Example usage
### Create instance
You can use either **new DietrichEvents2(exception -> {});** or **DietrichEvents2.global()** to access an instance of the EventSystem,

### Create an Event
```java
public interface ExampleListener {

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
DietrichEvents2.global().postInternal(ExampleEvent.ID, new ExampleEvent("Hello World!"));
````

## JMH Benchmark
The Benchmark shows the average time it takes to call an event 100.000 times. It should be noted that all event systems were run in the same benchmark implementation (see src/jmh), the same hardware and the same Java version (latest Java 8).


### Java 17
| Benchmark                                                                     | Mode | Cnt | Score       | Error      | Units |
|-------------------------------------------------------------------------------|------|-----|-------------|------------|-------|
| [DietrichEvents2](https://github.com/FlorianMichael/DietrichEvents2)          | avgt | 4   | 310318,125  | 124933,800 | ns/op |
| [DietrichEvents](https://github.com/FlorianMichael/DietrichEvents)            | avgt | 4   | 717058,403  | 6957,014   | ns/op |
| [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) (LambdaMetaFactory) | avgt | 4   | 1134071,045 | 41718,145  | ns/op |
| [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) (Method Handles)    | avgt | 4   | 1593772,392 | 66639,866  | ns/op |
| [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) (Reflection)        | avgt | 4   | 2164632,230 | 49576,755  | ns/op |

### Java 11
| Benchmark                                                                     | Mode | Cnt | Score       | Error      | Units |
|-------------------------------------------------------------------------------|------|-----|-------------|------------|-------|
| [DietrichEvents2](https://github.com/FlorianMichael/DietrichEvents2)          | avgt | 4   | 415349,165  | 7314,048   | ns/op |
| [DietrichEvents](https://github.com/FlorianMichael/DietrichEvents)            | avgt | 4   | 973776,952  | 32296,449  | ns/op |
| [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) (LambdaMetaFactory) | avgt | 4   | 1130460,687 | 30560,981  | ns/op |
| [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) (Method Handles)    | avgt | 4   | 1504976,295 | 69021,509  | ns/op |
| [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) (Reflection)        | avgt | 4   | 2205444,424 | 337975,180 | ns/op |

### Java 8
| Benchmark                                                                     | Mode | Cnt | Score       | Error     | Units |
|-------------------------------------------------------------------------------|------|-----|-------------|-----------|-------|
| [DietrichEvents2](https://github.com/FlorianMichael/DietrichEvents2)          | avgt | 4   |  635392,941 | 25647,033 | ns/op |
| [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) (LambdaMetaFactory) | avgt | 4   | 1129216,906 | 16383,663 | ns/op |
| [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) (Method Handles)    | avgt | 4   | 1495215,777 | 6336,098  | ns/op |
| [DietrichEvents](https://github.com/FlorianMichael/DietrichEvents)            | avgt | 4   | 1734243,043 | 11019,632 | ns/op |
| [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) (Reflection)        | avgt | 4   | 2169950,957 | 50804,811 | ns/op |

This table is not meant to put others down, but simply to support the main message of this event system.
