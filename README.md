### Spring Social RUNET-ID

To check out the project and build from source, do the following:

```
git clone git://github.com/indigobyte/spring-social-runetid.git
cd spring-social-runetid
./gradlew build
```

To generate Eclipse metadata (.classpath and .project files), do the following:

```
./gradlew eclipse
```

Once complete, you may then import the projects into Eclipse as usual:
 `File -> Import -> Existing projects into workspace`

To generate IDEA metadata (.iml and .ipr files), do the following:

```
./gradlew idea
```

To build the JavaDoc, do the following from within the root directory:

```
./gradlew :docs:api
```

The result will be available in `'docs/build/api'`.

For more details go to project wiki https://github.com/indigobyte/spring-social-runetid/wiki

To create maven artifact:

Create new empty directory `maven-temp` in the project root.

Checkout spring-social-runetid into maven-temp.

Switch to branch mvn-repo. You should have directory structure like this:

```
c:\projects\spring-social-runetid\maven-temp\spring-social-runetid\org\springframework\social\spring-social-runetid\1.0.0\maven-metadata.xml
```

Go to root project dir (`c:\projects\spring-social-runetid`).

run command

```
./gradlew clean build publish
```

Go back to `maven-temp\spring-social-runetid` folder:

```
c:\projects\spring-social-runetid\maven-temp\spring-social-runetid\org\springframework\social\spring-social-runetid\
```

Commit and push mvn-repo branch.