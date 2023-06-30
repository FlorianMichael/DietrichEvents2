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
DietrichEvents2.global().postInternal(ExampleEvent.ID, new ExampleEvent("Hello World!"));
````

## JMH Benchmark
The Benchmark shows the average time it takes to call an event 100.000 times. It should be noted that all event systems were run in the same benchmark implementation (see src/jmh), the same hardware and the same Java version (latest Java 17).

| Benchmark                                                                     | Mode | Cnt | Score        | Error      | Units |
|-------------------------------------------------------------------------------|------|-----|--------------|------------|-------|
| [DietrichEvents2](https://github.com/FlorianMichael/DietrichEvents2)          | avgt | 4   | 315071,130   | 189479,490 | ns/op |
| [DietrichEvents](https://github.com/FlorianMichael/DietrichEvents)            | avgt | 4   | 716681.647   | 145474,182 | ns/op |
| [ASMEvents](https://github.com/Lenni0451/ASMEvents)                           | avgt | 4   | 828250,494   | 84037,890  | ns/op |
| [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) (LambdaMetaFactory) | avgt | 4   | 1132881,936  | 34955,873  | ns/op |
| [Needle](https://github.com/lumii500pg/Needle) EventSystem                    | avgt | 4   | 1191812,547  | 50535,057  | ns/op |
| [EventAPI](https://github.com/Lenni0451/EventAPI) (ASMEventManager)           | avgt | 4   | 1376700,792  | 89812,282  | ns/op |
| [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) (MethodHandles)     | avgt | 4   | 1512119,287  | 48684,697  | ns/op |
| [DarkMagician6](https://bitbucket.org/DarkMagician6/eventapi/src/master/)     | avgt | 4   | 1867717,843  | 100388,819 | ns/op |
| [EventAPI](https://github.com/Lenni0451/EventAPI) (MinimalEventManager)       | avgt | 4   | 2010686,168  | 222143,047 | ns/op |
| [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) (Reflection)        | avgt | 4   | 2187790,081  | 84823,084  | ns/op |
| [EventAPI](https://github.com/Lenni0451/EventAPI) (InjectionEventManager)     | avgt | 4   | 3988598,320  | 81180,767  | ns/op |
| [EventAPI](https://github.com/Lenni0451/EventAPI) (EventManager)              | avgt | 4   | 5787144,894  | 508915,190 | ns/op |
| [Cydhra](https://github.com/Cydhra/EventSystem/tree/master) EventSystem       | avgt | 4   | 7245875.479  | 153210,639 | ns/op |
| [Guava](https://github.com/google/guava)                                      | avgt | 4   | 11210296,024 | 306451,725 | ns/op |

This table is not meant to put others down, but simply to support the main message of this event system.