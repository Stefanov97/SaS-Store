const URLS = {
    products: '/api/shopping-cart',
};

const toString = (item) => {
    let columns = `
    <td>${item.product.brand}</td>
    <td>${item.product.model}</td>
    <td>${(item.product.price * item.quantity).toFixed(2)} BGN</td>
    <td>${item.quantity}</td>
    <td><a class="btn btn-danger m-left-40" href="/remove-from-cart/${item.product.model}">Remove</a></td>
    <td><img src="${item.product.imageUrl}" width="100" height="100"></td>`;

    return `<tr class="bg-blur">${columns}</tr>`
};

fetch(URLS.products)
    .then(response => response.json())
    .then(items => {
        let totalPrice = 0;
        let result = '';
        items.forEach(item => {
            const itemString = toString(item);
            totalPrice += item.product.price * item.quantity;
            result += itemString;
        });

        $('#products-in-card').html(result);
        $('#cart-total-price').html('Total Price: ' + totalPrice.toFixed(2) + ' BGN');
    });

