FROM styl3r/vanilla:latest

LABEL version="1.0 Docker"
LABEL description="Insuradar NER"


RUN ["mkdir", "-p", "/apps/ner"]
COPY . /apps/ner

WORKDIR /apps/ner

ENTRYPOINT ["./docker-entry.sh"]


