# DietrichEvents2
One of the fastest Java event systems in the world using compiler optimizations, which still has a lot of features

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
The Benchmark shows the average time it takes to call an event 100.000 times.
All Benchmarks are run with the same code (see **src/jmh/java**), but different Java versions. If an event system does not appear in every list, it does not exist for the particular Java version, or would not work without modifying it. <br>

### Hardware specification:
- CPU: Intel(R) Core(TM) i9-10900K CPU @ 3.70GHz
- RAM: 48,0GB DDR4
- GPU: NVIDIA GeForce RTX 3070 Ti
- OS: Windows 11 Home 22H2 (Build 22621.1992)

If you want to have another event system in the list, or want to have the source code to generate the tables, you can write me on Discord, look for it at "Contact" above. <br>

### Java 17
| Benchmark                                                                                                               | Mode | Cnt | Score        | Error      | Units |
|-------------------------------------------------------------------------------------------------------------------------|------|-----|--------------|------------|-------|
| [DietrichEvents2](https://github.com/FlorianMichael/DietrichEvents2)                                                    | avgt | 4   | 310318,125   | 124933,800 | ns/op |
| [ASMEvents](https://github.com/Lenni0451/ASMEvents)                                                                     | avgt | 4   | 546376,840   | 18561,729  | ns/op |
| [ChimeraEventBus](https://github.com/FelixH2012/ChimeraEventBus)                                                        | avgt | 4   | 581672,678   | 71045,952  | ns/op |
| [norbit](https://github.com/CrosbyDev/norbit)                                                                           | avgt | 4   | 604412,122   | 28715,740  | ns/op |
| [DietrichEvents](https://github.com/FlorianMichael/DietrichEvents)                                                      | avgt | 4   | 627457,818   | 12842,704  | ns/op |
| [EventAPI](https://github.com/Lenni0451/EventAPI) (MinimalEventManager)                                                 | avgt | 4   | 769492,673   | 18975,650  | ns/op |
| [DarkMagician6](https://bitbucket.org/DarkMagician6/eventapi/src/master/)                                               | avgt | 4   | 1020463,350  | 70117,174  | ns/op |
| [Needle](https://github.com/sugisaru/Needle) EventSystem                                                                | avgt | 4   | 1021693,491  | 24196,860  | ns/op |
| [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) (LambdaMetaFactory)                                           | avgt | 4   | 1134071,045  | 41718,145  | ns/op |
| [Needle](https://github.com/sugisaru/Needle/tree/2214a4c1c1b6253d5bc67fbba11fc534d3ec055d) EventSystem (KitsuneAlex PR) | avgt | 4   | 1209510,353  | 152778,837 | ns/op |
| [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) (Method Handles)                                              | avgt | 4   | 1593772,392  | 66639,866  | ns/op |
| [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) (Reflection)                                                  | avgt | 4   | 2164632,230  | 49576,755  | ns/op |
| [Cydhra](https://github.com/Cydhra/EventSystem/tree/master) (Event System)                                              | avgt | 4   | 5169086,080  | 58597,729  | ns/op |
| [EventAPI](https://github.com/Lenni0451/EventAPI) (EventManager)                                                        | avgt | 4   | 6735240,280  | 221306,805 | ns/op |
| [Guava](https://github.com/google/guava)                                                                                | avgt | 4   | 15337145,465 | 247949,530 | ns/op |

### Java 11
| Benchmark                                                                                                               | Mode | Cnt | Score        | Error      | Units |
|-------------------------------------------------------------------------------------------------------------------------|------|-----|--------------|------------|-------|
| [DietrichEvents2](https://github.com/FlorianMichael/DietrichEvents2)                                                    | avgt | 4   | 415349,165   | 7314,048   | ns/op |
| [ASMEvents](https://github.com/Lenni0451/ASMEvents)                                                                     | avgt | 4   | 663676,846   | 16997,570  | ns/op |
| [ChimeraEventBus](https://github.com/FelixH2012/ChimeraEventBus)                                                        | avgt | 4   | 710557,890   | 72090,826  | ns/op |
| [DietrichEvents](https://github.com/FlorianMichael/DietrichEvents)                                                      | avgt | 4   | 743307,467   | 18786,064  | ns/op |
| [DarkMagician6](https://bitbucket.org/DarkMagician6/eventapi/src/master/)                                               | avgt | 4   | 753029,999   | 16373,710  | ns/op |
| [EventAPI](https://github.com/Lenni0451/EventAPI) (MinimalEventManager)                                                 | avgt | 4   | 770001,532   | 24463,218  | ns/op |
| [EventAPI](https://github.com/Lenni0451/EventAPI) (ASMEventManager)                                                     | avgt | 4   | 771670,427   | 13801,284  | ns/op |
| [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) (LambdaMetaFactory)                                           | avgt | 4   | 1130460,687  | 30560,981  | ns/op |
| [Needle](https://github.com/sugisaru/Needle) EventSystem                                                                | avgt | 4   | 1212077,331  | 67071,275  | ns/op |
| [Needle](https://github.com/sugisaru/Needle/tree/2214a4c1c1b6253d5bc67fbba11fc534d3ec055d) EventSystem (KitsuneAlex PR) | avgt | 4   | 1360801,867  | 24181,835  | ns/op |
| [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) (Method Handles)                                              | avgt | 4   | 1504976,295  | 69021,509  | ns/op |
| [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) (Reflection)                                                  | avgt | 4   | 2205444,424  | 337975,180 | ns/op |
| [EventAPI](https://github.com/Lenni0451/EventAPI) (InjectionEventManager)                                               | avgt | 4   | 3015519,829  | 44426,039  | ns/op |
| [EventAPI](https://github.com/Lenni0451/EventAPI) (EventManager)                                                        | avgt | 4   | 5504772,250  | 46947,848  | ns/op |
| [Cydhra](https://github.com/Cydhra/EventSystem/tree/master) (Event System)                                              | avgt | 4   | 5794477,294  | 113790,263 | ns/op |
| [Guava](https://github.com/google/guava)                                                                                | avgt | 4   | 11656575,419 | 533926,166 | ns/op |

### Java 8
| Benchmark                                                                                                               | Mode | Cnt | Score        | Error      | Units |
|-------------------------------------------------------------------------------------------------------------------------|------|-----|--------------|------------|-------|
| [DietrichEvents2](https://github.com/FlorianMichael/DietrichEvents2)                                                    | avgt | 4   | 635392,941   | 25647,033  | ns/op |
| [ASMEvents](https://github.com/Lenni0451/ASMEvents)                                                                     | avgt | 4   | 813149,931   | 31763,759  | ns/op |
| [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) (LambdaMetaFactory)                                           | avgt | 4   | 1129216,906  | 16383,663  | ns/op |
| [EventAPI](https://github.com/Lenni0451/EventAPI) (MinimalEventManager)                                                 | avgt | 4   | 1327251,543  | 55359,321  | ns/op |
| [EventAPI](https://github.com/Lenni0451/EventAPI) (ASMEventManager)                                                     | avgt | 4   | 1399196,527  | 55170,229  | ns/op |
| [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) (Method Handles)                                              | avgt | 4   | 1495215,777  | 6336,098   | ns/op |
| [DietrichEvents](https://github.com/FlorianMichael/DietrichEvents)                                                      | avgt | 4   | 1497398,449  | 128637,113 | ns/op |
| [Needle](https://github.com/sugisaru/Needle) EventSystem                                                                | avgt | 4   | 1510824,164  | 55351,446  | ns/op |
| [Needle](https://github.com/sugisaru/Needle/tree/2214a4c1c1b6253d5bc67fbba11fc534d3ec055d) EventSystem (KitsuneAlex PR) | avgt | 4   | 1633057,398  | 45904,437  | ns/op |
| [DarkMagician6](https://bitbucket.org/DarkMagician6/eventapi/src/master/)                                               | avgt | 4   | 1870394,664  | 59848,353  | ns/op |
| [LambdaEvents](https://github.com/Lenni0451/LambdaEvents) (Reflection)                                                  | avgt | 4   | 2169950,957  | 50804,811  | ns/op |
| [EventAPI](https://github.com/Lenni0451/EventAPI) (InjectionEventManager)                                               | avgt | 4   | 3832627,003  | 73982,528  | ns/op |
| [EventAPI](https://github.com/Lenni0451/EventAPI) (EventManager)                                                        | avgt | 4   | 5963389,296  | 956568,797 | ns/op |
| [Cydhra](https://github.com/Cydhra/EventSystem/tree/master) (Event System)                                              | avgt | 4   | 7357398,698  | 92774,097  | ns/op |
| [Guava](https://github.com/google/guava)                                                                                | avgt | 4   | 11945301,707 | 239846,530 | ns/op |

*This table is not meant to put others down, but simply to support the main message of this event system.*
