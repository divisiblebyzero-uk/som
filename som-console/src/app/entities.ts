export interface ReceiveAgainstPayment {
    id?: number,
    senderReference: String,
    senderPartyId: String,
    senderAccountId: String,
    instrumentId: String,
    receiverPartyId: String,
    receiverAccountId: String,
    currency: String,
    amount: number,
    status?: String
}
