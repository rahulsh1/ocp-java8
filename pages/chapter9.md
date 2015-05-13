---
layout: page
title: Chapter 9. JavaScript on Java with Nashorn
description: 1Z0-810 Java SE8
comments: true
---

### 9.1 Develop Javascript code that creates and uses Java members such as Java objects, methods, JavaBeans, Arrays, Collections, Interfaces.

Nashorn is the only JavaScript engine included in the JDK. However, you can use any script engine compliant with JSR 223

To get an instance of the Nashorn engine, pass in "nashorn".
Alternatively, you can use any of the following: "Nashorn", "javascript", "JavaScript", "js", "JS", "ecmascript", "ECMAScript".

Command line tools:
{% highlight java  %}  
jrunscript : Invokes any available script engine
jjs        : To evaluate a script file using Nashorn, 
             pass the name of the script file to the jjs tool.
{% endhighlight %} 
 
{% highlight java  %} 
jjs> java.lang
[JavaPackage java.lang]
jjs> typeof java.lang
object
jjs> java.lang.System
[JavaClass java.lang.System]
jjs> typeof java.lang.System
function
{% endhighlight %} 
 
Nashorn interprets Java packages as JavaPackage objects, and Java classes as JavaClass function objects, which can be used as constructors for the classes.

The `Java.type()` function takes a string with the fully qualified Java class name, and returns the corresponding JavaClass function object. 

{% highlight java  %} 
jjs> var HashMap = Java.type("java.util.HashMap")
jjs> var mapDef = new HashMap()
jjs> mapDef.put('a', 'val');
jjs> mapDef.get('a');
val
jjs> mapDef.get('b');
null
jjs> mapDef.put('b', 'val2');
null
jjs> mapDef.get('b');
val2
{% endhighlight %} 

##### Accessing Class and Instance Members
{% highlight java  %} 
jjs> Java.type("java.lang.Math").PI
3.141592653589793

jjs> Java.type("java.lang.System").currentTimeMillis()
1429378149988
{% endhighlight %} 

##### Extending Java Classes/Interfaces

  You can extend a class using the Java.extend() function that takes a Java type as the first argument and 
  method implementations (in the form of JavaScript functions) as the other arguments. 
  Below shows a script that extends the java.lang.Runnable interface and uses it to construct a new java.lang.Thread object.

{% highlight java  %} 
var Run = Java.type("java.lang.Runnable");
var MyRun = Java.extend(Run, {
    run: function() {
        print("Run in separate thread");
    }
});
var Thread = Java.type("java.lang.Thread");
var th = new Thread(new MyRun());
{% endhighlight %} 

Nashorn can automatically extend single abstract method (SAM) classes if you provide the function for implementing the method as the argument to the constructor.

{% highlight java  %} 
var Thread = Java.type("java.lang.Thread")
var th = new Thread(function() print("Run in a separate thread"))
{% endhighlight %} 

##### Restricting Script Access to Specified Java Classes
  The `jdk.nashorn.api.scripting.ClassFilter` interface enables you to restrict access to specified Java classes from scripts run by a Nashorn script engine

--------------------------------	

:memo: [Code examples](https://github.com/rahulsh1/ocp-java8/tree/master/sources/src/ocp/study/part9)


--------------------------------	    
[Previous Chapter 8. Use Java SE 8 Date/Time API](chapter8.html)

--------------------------------

