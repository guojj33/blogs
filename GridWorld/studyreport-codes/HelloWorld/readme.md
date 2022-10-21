## 运行

### 方式一、 使用ant

```
ant clean
ant run
ant junit
```

### 方式二、使用命令

编译
```
mkdir -p build/classes
javac src/HelloWorldTest.java src/helloWorld/HelloWorld.java -d build/classes/ -cp .:lib/junit-4.10.jar
```

运行
```
java -cp .:build/classes:lib/junit-4.10.jar helloWorld.HelloWorld
java -cp .:build/classes:lib/junit-4.10.jar org.junit.runner.JUnitCore HelloWorldTest
```