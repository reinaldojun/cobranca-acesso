#/bin/sh
DOMAIN=$(echo "$CI_BUILD_URL" | awk -F/ '{print $3}')
export SERVICE_HOSTNAME=${DOMAIN}

echo $SERVICE_HOSTNAME > /tmp/start.log

JVM_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1 "

if [ "$1" = "Deploy:production" ]; then
   echo "perfil=producao" > /config/perfil.properties
   SPRING_PROFILES="prod"
elif [ "$1" = "Deploy:staging" ]; then
   echo "perfil=homologacao" > /config/perfil.properties
   SPRING_PROFILES="hom"
elif [ "$1" = "Deploy:review-staging" ]; then
   echo "perfil=homologacao" > /config/perfil.properties
   SPRING_PROFILES="hom"
elif [ "$1" = "Deploy:preprod" ]; then
   echo "perfil=preproducao" > /config/perfil.properties
   SPRING_PROFILES="prod"
else
   echo "perfil=desenvolvimento" > /config/perfil.properties
   SPRING_PROFILES="dev"
fi

export CI_ENVIRONMENT=$1

java $JVM_OPTS  -Dserver.port=80 -jar /tmp/*.jar -Djava.net.preferIPv4Stack=true -Dfile.encoding=UTF-8 --spring.profiles.active=$SPRING_PROFILES --eureka.instance.hostname=${DOMAIN}

