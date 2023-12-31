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
transactions = read_transactions_from_file("./input_retail.txt")

# Extract unique products from the transactions
unique_products = set(product for transaction in transactions for product in transaction)

# Define a function to generate random probabilities
def generate_random_probabilities(products):
    return [random.random() for _ in products]

# Generate random existential probabilities for each transaction
existential_probabilities = []

for transaction in transactions:
    probabilities = generate_random_probabilities(unique_products)
    existential_probabilities.append(probabilities)

# Write the products and existential probabilities to a text file
with open("./ep_retail.txt", "w") as file:
    file.write(" ".join(map(str, unique_products)) + "\n")
    for probabilities in existential_probabilities:
        file.write(" ".join(f"{p:.1f}" for p in probabilities) + "\n")

print("Results have been saved to 'Foodmart_ep.txt'.")