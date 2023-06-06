export interface Transfer {
    transactionId: string;
    sourceAccount: string;
    targetAccountId: string;
    amount: number;
    transferStatus: string;
    startedAt: string;
    finishedAt: string;
}
