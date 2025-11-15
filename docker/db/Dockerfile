FROM postgres:15

EXPOSE 5432

WORKDIR /docker-entrypoint-initdb.d

COPY *.sql .

ENV POSTGRES_DB=tdl
