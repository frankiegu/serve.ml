Based on NetflixOSS [Dynomite](https://github.com/Netflix/) + [Spark ML](http://spark.apache.org) + [TensorFlow](http://redis.io)

## Prequisites
* None

# Spark ML Serving
## Start Docker Container
```
docker run -itd --name=serve-jvm --privileged --net=host -p 9040:9040 fluxcapacitor/serve-jvm
```

## Verify Successful Start through Logs
```
docker logs -f serve-jvm
```

## Verify Successful Start through Kafka REST API
```
http://<server-ip>:9040/prediction/21619/10001

*** EXPECTED OUTPUT ***
...
TODO:  
...
```

## (Optional) Build new Docker Image
```
cd jvm

docker build -t fluxcapacitor/serve-jvm .
```


# TensorFlow Serving
## Start Docker Container
```
docker run -itd --name=serve-tensorflow-0.4.1 --privileged --net=host -p 9040:9040 fluxcapacitor/serve-tensorflow-0.4.1
```

## Verify Successful Start through Logs
```
docker logs -f serve-tensorflow-0.4.1
```

## Verify Successful Start through gRPC API
```
TODO: 

*** EXPECTED OUTPUT ***
...
TODO:  
...
```

## (Optional) Build new Docker Image
```
cd tensorflow/0.4.1

docker build -t fluxcapacitor/serve-tensorflow-0.4.1 .
```
