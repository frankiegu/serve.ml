FROM fluxcapacitor/package-ubuntu-14.04

# Notes:
#   The contents ond tools installed in this Dockerfile have only been tested on Ubuntu 14.04.
#   Use at your own risk if you are trying to apply these instructions to a different environment.
#   We've done our best to highlight (Optional) installs - usually around system-level performance monitoring tools like "perf" from the linux-tools package.
#   Feel free to leave out these installs, but you may lose compatibility with future releases of this distribution.
#   It's highly-advised that you run this distributed of Docker/Ubuntu on whatever host system you are running (ie. RHEL, CentOS, etc)

# These are environment variables that match the versions of the sofware tools installed by this Dockerfile.
# We also need to include library dependency versions as we trigger a build of all Scala/Java-based source code
#  at the end in order to pre-bake the dependencies into the Docker image.  This saves time and network bandwidth later.
#
ENV \
 LOGSTASH_VERSION=2.3.0 \
 REDIS_VERSION=3.0.5 \
 SBT_VERSION=0.13.11 \
 SBT_ASSEMBLY_PLUGIN_VERSION=0.14.0 \
 HADOOP_VERSION=2.6.0 \
 HIVE_VERSION=1.2.1 \
 SCALA_VERSION=2.10.5 \
 SCALA_MAJOR_VERSION=2.10 \
 SPARK_VERSION=1.6.1 \
 KAFKA_CLIENT_VERSION=0.10.0.0 \
 JEDIS_VERSION=2.7.3 \
 JBLAS_VERSION=1.2.4 \
 BAZEL_VERSION=0.2.2 \
 TENSORFLOW_VERSION=0.9.0 \
 TENSORFLOW_SERVING_VERSION=0.4.1 \
# JAVA_HOME required here (versus config/bash/pipeline.bashrc)
#   in order to properly install Bazel (used by TensorFlow)
 JAVA_HOME=/usr/lib/jvm/java-8-oracle \
 HYSTRIX_VERSION=1.5.3 \
 JANINO_VERSION=2.7.8 \
 SPARK_REDIS_CONNECTOR_VERSION=0.2.0 \
 DYNO_VERSION=1.4.6 \
 SPRING_BOOT_VERSION=1.3.5.RELEASE \
 SPRING_CLOUD_VERSION=1.1.2.RELEASE \
 SPRING_CORE_VERSION=4.3.0.RELEASE \
 FINAGLE_VERSION=6.34.0 \
 SCALATEST_VERSION=2.2.4 \
 BETTER_FILES_VERSION=2.14.0 \
 CODAHALE_METRICS_VERSION=3.1.2 \
 GUAVA_VERSION=14.0.1 \
 JMETER_VERSION=3.0 \
 JPMML_SPARKML_VERSION=1.0.4 \
 PMML_MODEL_METRO_VERSION=1.2.15 \
 PMML_MODEL_VERSION=1.2.15 \
 PMML_EVALUATOR_VERSION=1.2.14 \
 SPRING_PROFILES_ACTIVE=cluster

WORKDIR /root

RUN \
 apt-get update \
 && apt-get install -y software-properties-common \
 && add-apt-repository ppa:webupd8team/java \
 && apt-get update \
 && echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections \
 && apt-get install -y oracle-java8-installer \
 && apt-get install -y oracle-java8-set-default \
 && apt-get install -y curl \
 && apt-get install -y wget \
 && apt-get install -y vim \
 && apt-get install -y git \
 && apt-get install -y openssh-server \
 && apt-get install -y apache2 \
 && apt-get install -y libssl-dev \

# Maven for custom builds
 && apt-get install -y maven 

RUN \
 cd ~ \
 && wget https://dl.bintray.com/sbt/native-packages/sbt/${SBT_VERSION}/sbt-${SBT_VERSION}.tgz \
 && tar xvzf sbt-${SBT_VERSION}.tgz \
 && rm sbt-${SBT_VERSION}.tgz \
 && ln -s /root/sbt/bin/sbt /usr/local/bin \
# Sbt Clean - This seems weird, but this triggers the full Sbt install.
#             This retrievees a lot of external dependencies,
#               so we get it out of the way at Docker build time.
 && sbt clean clean-files

COPY config/ config/
COPY discovery/ discovery/ 
COPY turbine/ turbine/
COPY spark/ spark/
COPY prediction/ prediction/
COPY tensorflow/ tensorflow/

RUN \
# Mvn Config Service (Spring + Netflix)
 cd ~/config && mvn -DskipTests clean install \

# Mvn Discovery Service (Netflix Eureka)
 && cd ~/discovery && mvn -DskipTests clean install \

# Mvn Cluster-wide Circuit Breaker Metrics Service (Netflix Turbine)
 && cd ~/turbine && mvn -DskipTests clean install \

# Sbt Spark Serving
 && cd ~/spark && sbt clean package \

# Sbt Serving Prediction Service (Spring + Netflix)
 && cd ~/prediction && sbt clean package \

# Sidecar for TensorFlow Serving
 && cd ~/tensorflow/ && mvn -DskipTests clean install 

# Ports to expose
EXPOSE 80 6042 9160 9042 9200 7077 8080 8081 6060 6061 6062 6063 6064 6065 8090 10000 50070 50090 9092 6066 9000 19999 6081 7474 8787 5601 8989 7979 4040 4041 4042 4043 4044 4045 4046 4047 4048 4049 4050 4051 4052 4053 4054 4055 4056 4057 4058 4059 4060 6379 8888 54321 8099 8754 7379 6969 6970 6971 6972 6973 6974 6975 6976 6977 6978 6979 6980 5050 5060 7060 8182 9081 8998 9090 5080 5090 5070 8000 8001 6006 3060 9040 8102 22222 10080 5040 8764 7101 5678

WORKDIR /root

COPY run run

CMD ["supervise", "."]
