# Welcome to Money Transfer

This project aims to simulate the banking transfer system.
At the first stage the idea is to implement a single bank, on which users will be able to perform the following actions:
- Register himself as a user
- Open savings accounts (these accounts will start with a defined amount of money)
- Transfer money to other accounts in the same bank.

Once this is done, I'll create a Transfer Hub system, that will allow to connect multiple banks so users are allowed to transfer money between banks.

# Repository Folders

- Bank will contain all the specific projects to the bank, namely the (savings) accounts and transfers micro-services.
- TransferHub will contain all the specific projects to the hub.
- Every other project outside, is a shared project:
  - Discovery Service
  - Gateway Service
  - Configuration Service

# Bank Services
To run a bank service, please open a terminal and move into the bank folder just run:

    docker compose up -d

Additionally, you can change the contents of the .env file before running the compose command: 

| VARIABLE | DEFAULT VALUE | DESCRIPTION |
|--|--|--|
| BANK_SHORT | bank | Name of the bank, intended for use when multiple banks are used |
| NETWORK | 10.34.58 | Will define the network where the bank will be held |
| [SERVICE]_PORT | | Will override the default service port |
| [SERVICE]_NAME | | Will override the name of the service name |

# Bank Infrastructure
To run the bank infrastructure project, please open a terminal and move into the bank/infrastructure folder just run:

    docker compose --env-file ../.env up -d