# sinor-cache-back_websocket

### From [@oomia/websocket_js](https://github.com/ooMia/websocket_js/)

- rabbitMQ 브로커를 통해 amqp 서버와 연동되는 stomp 클라이언트 코드
- `node app.js`
  - 내부적으로 `http-test.js`를 참조하여 http 호출 결과를 동적으로 활용한 시나리오 테스트 구현
- `node client.js`
  - 특정 voteId에 대한 구독으로 메세지가 정상적으로 배포되는지 확인
