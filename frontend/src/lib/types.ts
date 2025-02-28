export interface Account {
    id: number;
    name: string;
    balance: number;
}

export interface Transaction {
    id: number;
    accountId: number;
    amount: number;
    description: string;
    date: Date;
}