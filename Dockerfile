FROM java:8
COPY . /app
WORKDIR /app
CMD ["gradle", "bootRun"]