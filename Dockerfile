FROM mysql:latest
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=testdb
ENV MYSQL_USER=user
ENV MYSQL_PASSWORD=password
EXPOSE 3306
CMD ["mysqld"]