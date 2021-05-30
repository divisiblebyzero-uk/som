import { InjectableRxStompConfig } from '@stomp/ng2-stompjs';
import { environment } from '../environments/environment';

export const somStompConfig: InjectableRxStompConfig = {

    brokerURL: environment.apiWebsocket + '/api/som-websocket',
    heartbeatIncoming: 0,
    heartbeatOutgoing: 20000,
    reconnectDelay: 200,
    debug: (msg: string): void => {
        console.log(new Date(), msg);
    }
}