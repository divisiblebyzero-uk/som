import { InjectableRxStompConfig } from '@stomp/ng2-stompjs';

export const somStompConfig: InjectableRxStompConfig = {
    brokerURL: 'ws://localhost:8080/api/som-websocket',
    heartbeatIncoming: 0,
    heartbeatOutgoing: 20000,
    reconnectDelay: 200,
    debug: (msg: string): void => {
        console.log(new Date(), msg);
    }
}