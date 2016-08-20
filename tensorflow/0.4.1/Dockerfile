FROM fluxcapacitor/utils-ubuntu-14.04
FROM fluxcapacitor/utils-java-1.8
FROM fluxcapacitor/utils-kafka-0.10

WORKDIR /root

ENV \
 TENSORFLOW_VERSION=0.9.0 \
 TENSORFLOW_SERVING_VERSION=0.4.1 \
 BAZEL_VERSION=0.2.2

RUN \
 apt-get update \
 && apt-get install -y python-dev \
 && apt-get install -y python-pip \
 && apt-get install -y git \
 && apt-get install -y wget \
 && apt-get install -y unzip

RUN \
 wget https://github.com/bazelbuild/bazel/releases/download/$BAZEL_VERSION/bazel-$BAZEL_VERSION-installer-linux-x86_64.sh \
 && chmod +x bazel-$BAZEL_VERSION-installer-linux-x86_64.sh \
 && ./bazel-$BAZEL_VERSION-installer-linux-x86_64.sh --bin=/root/bazel-$BAZEL_VERSION/bin \
 && rm bazel-$BAZEL_VERSION-installer-linux-x86_64.sh \
 && export PATH=$PATH:/root/bazel-${BAZEL_VERSION}/bin/

RUN \
 pip install --upgrade https://storage.googleapis.com/tensorflow/linux/cpu/tensorflow-$TENSORFLOW_VERSION-cp27-none-linux_x86_64.whl

RUN \
 pip install grpcio \
 && apt-get install -y \
      build-essential \
      libfreetype6-dev \
      libpng12-dev \
      libzmq3-dev \
      pkg-config \
      python-dev \
      python-numpy \
      python-pip \
      software-properties-common \
      swig \
      zip \
      zlib1g-dev

RUN \
# TODO:  Figure out why '-b $TENSORFLOW_SERVING_VERSION --single-branch --recurse-submodules' is not working here...
 git clone https://github.com/tensorflow/serving.git

# TODO:  Get these working
#RUN \
#  echo '...Configuring TensorFlow...' \
#  && cd serving/tensorflow \
#  && echo | ./configure

#RUN \
#  echo '...Build the Inception Image Classifier Components...' \
#  && cd serving \
#  && bazel build //tensorflow_serving/example:inception_inference \
#  && bazel build //tensorflow_serving/example:inception_client \
#  && bazel build //tensorflow_serving/example:inception_export
#
#RUN \
#  echo '...Build the MNIST Image Classifier Components (10-15 mins)...' \
#  && cd serving \
#  && bazel build //tensorflow_serving/example:mnist_inference_2 \
#  && bazel build //tensorflow_serving/example:mnist_client \
#  && bazel build //tensorflow_serving/example:mnist_export

COPY bin/ bin/

CMD ["bin/start.sh"]
