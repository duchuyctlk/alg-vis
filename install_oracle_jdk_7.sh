#!/bin/bash

# check version
java -version
ls -l /etc/alternatives/java
ls /usr/lib/jvm/

# download, link: http://stackoverflow.com/a/10959815
wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/7u79-b15/jdk-7u79-linux-x64.tar.gz

# steps: http://askubuntu.com/a/55960
tar -xvf jdk-7u79-linux-x64.tar.gz
mv ./jdk1.7.0_79 ./jdk1.7.0
mv ./jdk1.7.0 /usr/lib/jvm/
update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/jdk1.7.0/bin/java" 1000
update-alternatives --install "/usr/bin/javac" "javac" "/usr/lib/jvm/jdk1.7.0/bin/javac" 1000
update-alternatives --install "/usr/bin/javaws" "javaws" "/usr/lib/jvm/jdk1.7.0/bin/javaws" 1000
chmod a+x /usr/bin/java
chmod a+x /usr/bin/javac
chmod a+x /usr/bin/javaws
chown -R root:root /usr/lib/jvm/jdk1.7.0
ls -l /etc/alternatives/java

# switch to JDK 7
update-alternatives --config java <<< "3"
update-alternatives --config javac <<< "3"
update-alternatives --config javaws <<< "1"

# check version again
java -version

