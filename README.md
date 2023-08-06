# [AXE Bank] -  RESTful API

This is a RESTful API that provides customers with a range of banking functionality. It allows users to manage their accounts, perform transactions and securely access account information.

## Table of Contents

- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Tests](#tests)
- [Contributing](#contributing)
- [License](#license)

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 11 or later installed.

### Installation

1. Clone this repository to your local machine.

```bash
git clone https://github.com/iivaylov/API-bank-back-end.git
```

2. Navigate to the project directory.

```bash
cd API-bank-back-end
```

3. Build the project using Maven

## Usage

By default, the application will be available at `http://localhost:8080`.

This application exposes various REST endpoints for interacting with the AXE Bank API. Here are some examples of how to use them:

1. Get account details:

```bash
GET http://localhost:8080/api/v1/accounts/{accountId}
```

2. Transfer funds:

```bash
POST http://localhost:8080/api/v1/accounts/{fromAccountId}/transfer
Content-Type: application/json

{
  "accountToIban": "AXE#U26VNZ1SBD0TPO1V",
  "amount": 100.00
}
```

## API Documentation

You can access the API documentation by visiting `http://localhost:8080/swagger-ui.html` when the application is running.

## Tests

### Run Single Test Class

Right-click on the test class and choose "Run 'ClassTest'" from the context menu.

Alternatively, you can open the test class, position the cursor inside the class, and use the keyboard shortcut: Ctrl + Shift + F10 (Windows/Linux) or Control + Shift + R (macOS).

### Run All Tests in the Project

If you want to run all tests in the entire project, you can right-click on the project root directory and choose "Run 'All Tests'" from the context menu.

Or, you can use the keyboard shortcut: Ctrl + Shift + F10 (Windows/Linux) or Control + Shift + R (macOS) while the focus is on the project root.

### View Test Results

After running the tests, the "Run" tool window will appear at the bottom of the IntelliJ IDE.

This window shows the test results, including the number of tests passed, failed, and the time taken for execution.

You can click on individual test cases to view their details.

## Contributing

Contributions to this project are welcome! If you find any issues or have ideas for improvements, feel free to open a new issue or submit a pull request.

## License

This repo is MIT licensed.
