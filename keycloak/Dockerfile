FROM quay.io/keycloak/keycloak:latest as builder

# Enable health and metrics support
ENV KC_HEALTH_ENABLED=true
ENV KC_METRICS_ENABLED=true

# Configure a database vendor
ENV KC_DB=mysql

WORKDIR /opt/keycloak
# for demonstration purposes only, please make sure to use proper certificates in production instead
RUN keytool -genkeypair -storepass password -storetype PKCS12 -keyalg RSA -keysize 2048 -dname "CN=server" -alias server -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -keystore conf/server.keystore
RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:latest
COPY --from=builder /opt/keycloak/ /opt/keycloak/

# 초기 설정값으로 변경해주어야 사용가능함.
ENV KC_DB=mysql
ENV KC_DB_URL=jdbc:mysql://mysqlHost:3306/user
ENV KC_DB_USERNAME=root
ENV KC_DB_PASSWORD=db-q5n2g
ENV KC_HOSTNAME=localhost

# 공식문서 최적화 포트
ENV KC_HTTP_PORT=8443

# 어드민 계정과 비밀번호
ENV KEYCLOAK_ADMIN=admin
ENV KEYCLOAK_ADMIN_PASSWORD=admin

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start", "--http-port=${KC_HTTP_PORT}"]

