import pandas as pd

def read_transactions(file_path):
    transactions = []
    all_items = set()
    with open(file_path, 'r') as file:
        for line in file:
            transaction = line.strip().split()
            transactions.append(transaction)
            all_items.update(transaction)
    return transactions, list(all_items)

def calculate_existential_probabilities(transactions, all_items):
    item_counts = {item: 0 for item in all_items}
    for transaction in transactions:
        for item in set(transaction):  # Loại bỏ trùng lặp trong mỗi giao dịch
            item_counts[item] += 1

    total_transactions = len(transactions)
    existential_probabilities = [{item: round(count / total_transactions,4) for item, count in item_counts.items()} for _ in range(total_transactions)]
    return existential_probabilities



def write_probabilities_to_file(probabilities, all_items, output_file_path):
    with open(output_file_path, 'w') as file:
        # Ghi hàng tiêu đề
        header = ' '.join(all_items)
        file.write(header + '\n')

        # Ghi dữ liệu xác suất tồn tại
        for prob_dict in probabilities:
            line = ' '.join(str(prob_dict.get(item, 0)) for item in all_items)  # Sử dụng phương thức get để tránh lỗi KeyError
            file.write(line + '\n')


input_file_path = 'D:\TDTU\HK1 2023 - 2024\CD nghien cuu 1\CDNC1_Mining-top-k-frequent-patterns-from-uncertain-databases\input_retail.txt'
output_file_path = 'D:\TDTU\HK1 2023 - 2024\CD nghien cuu 1\CDNC1_Mining-top-k-frequent-patterns-from-uncertain-databases\ep_retail.txt'

transactions, all_items = read_transactions(input_file_path)

# Tính xác suất tồn tại
probabilities = calculate_existential_probabilities(transactions, all_items)

# Ghi kết quả ra file
write_probabilities_to_file(probabilities, all_items, output_file_path)