.DEFAULT_GOAL := build-run

build-run: fast-build run

run: docker-db
	java --enable-preview -jar ./target/typo-*.jar

build-with-tests:
	./mvnw clean verify -T 1C

fast-build:
	./mvnw package -DskipTests=true -T 1C

verify:
	./mvnw clean verify -T 1C

docker-db:
	docker-compose -f ./src/main/docker/postgresql.yml up -d
