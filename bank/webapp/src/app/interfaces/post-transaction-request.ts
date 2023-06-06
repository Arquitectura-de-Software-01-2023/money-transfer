export interface PostTransactionRequest {
    sourceAccount: string;
    targetAccount: string;
    targetBank: string;
    amount: number;
}
