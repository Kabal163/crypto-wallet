FROM openjdk:11

ENV work_dir=/kabal163/cryptowallet
WORKDIR ${work_dir}

ARG logs_dir

COPY target/*.jar ${work_dir}/app.jar
RUN mkdir ${logs_dir}
RUN chmod a+rwx ${logs_dir}

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar ${work_dir}/app.jar"]