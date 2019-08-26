FROM styl3r/vanilla:latest

LABEL version="1.0 Docker"
LABEL description="Insuradar OCR"


RUN ["mkdir", "-p", "/apps/ocr"]
RUN ["mkdir", "-p", "/model"]
COPY . /apps/ocr

WORKDIR /apps/ocr
RUN pip2 install -r requirements.txt
RUN apt-get update && apt-get install -y software-properties-common
RUN add-apt-repository -y ppa:alex-p/tesseract-ocr
RUN apt-get update && apt-get install -y tesseract-ocr
WORKDIR /apps/ocr

EXPOSE 5005
EXPOSE 8080
ENTRYPOINT ["./docker-entry.sh"]


