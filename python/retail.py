import random


# Define a function to read transactions from a text file
def read_transactions_from_file(filename):
    transactions = []
    with open(filename, 'r') as file:
        for line in file:
            transaction = list(map(int, line.strip().split()))
            transactions.append(transaction)
    return transactions

# Read transactions from the text file
transactions = read_transactions_from_file("../input_retail.txt")

# Extract unique products from the transactions
unique_products = set(product for transaction in transactions for product in transaction)

# Function to generate random existential probabilities
def generate_random_probabilities(transactions, unique_products):
    existential_probabilities = []
    for transaction in transactions:
        probabilities = [random.random() if item in transaction else 0.0 for item in unique_products]
        existential_probabilities.append(probabilities)
    return existential_probabilities

# Generate random existential probabilities for each transaction
existential_probabilities = generate_random_probabilities(transactions, unique_products)

# Write the products and existential probabilities to a text file
with open("../ep_retail.txt", "w") as file:
    # Write the header with item numbers
    file.write(" ".join(map(str, unique_products)) + "\n")

    # Write the existential probabilities for each transaction
    for probabilities in existential_probabilities:
        file.write(" ".join(f"{p:.2f}" for p in probabilities) + "\n")

print("Results have been saved to 'your_output_file.txt'.")
