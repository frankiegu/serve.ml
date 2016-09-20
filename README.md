Based on NetflixOSS [Dynomite](https://github.com/Netflix/) + [Spark ML](http://spark.apache.org) + [TensorFlow](http://redis.io)

## Prequisites
* None

# Spark ML Serving
## Start Docker Container
```
docker run -itd --name=serve-sparkml --privileged --net=host -p 9040:9040 fluxcapacitor/serve-sparkml
```

## Verify Successful Start through Logs
```
docker logs -f serve-sparkml
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
cd spark/2.0.0

docker build -t fluxcapacitor/serve-sparkml .
```


# TensorFlow Serving
## Start Docker Container
```
docker run -itd --name=serve-tensorflow --privileged --net=host -p 9040:9040 fluxcapacitor/serve-tensorflow
```

## Verify Successful Start through Logs
```
docker logs -f serve-tensorflow
```

## Verify Successful Start through REST API
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

docker build -t fluxcapacitor/serve-tensorflow .
```
