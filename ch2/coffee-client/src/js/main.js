class CoffeeService {
  drinksSelect;
  sizeSelect;

  constructor() {
    //Get the HTML select element for drink and size
    this.drinksSelect = document.getElementById('drink');
    this.sizeSelect = document.getElementById('size');
    let createOrderButton = document.getElementById('createOrder');

    this.drinksSelect.onchange = e => {
      this.showPrice();
    };

    this.sizeSelect.onchange = e => {
      this.showPrice();
    };

    createOrderButton.onclick = e => {
      let size = this.sizeSelect.value;
      let drink = this.drinksSelect.value;
      let email = document.getElementById('email').value;
      this.orderCoffee(drink, email, size);
    };
  }

  init() {
    fetch('http://localhost:8020/coffee-drinks/resources/coffees')
      .then(response => response.json())
      .then(data => this.buildDropDown(data))
      .then(e => {
        this.drinksSelect.selectedIndex = 0;
        this.sizeSelect.selectedIndex = 0;
        this.showPrice();
      });
  }

  showPrice() {
    let selectedSize = this.sizeSelect.options[this.sizeSelect.selectedIndex].value;
    let amount = this.drinksSelect.options[this.drinksSelect.selectedIndex].getAttribute(
      `data-${selectedSize}`
    );

    let total = document.querySelector('#total');
    total.innerHTML = amount;
  }

  buildDropDown(data) {
    console.log('backend returned coffees', data);
    for (let i = 0; i < data.length; i++) {
      let drinkOption = document.createElement('option');
      drinkOption.text = data[i].type;
      drinkOption.value = data[i].type;

      for (let sizeOption in data[i].variantPrice) {
        drinkOption.setAttribute(`data-${sizeOption}`, data[i].variantPrice[sizeOption]);
      }
      this.drinksSelect.appendChild(drinkOption);
    }
  }

  orderCoffee(selectedDrink, customerEmail, selectedSize) {
    let ORDER_URL = 'http://localhost:8030/coffee-orders/resources/orders';
    // Create a new order
    fetch(ORDER_URL, {
      headers: { 'Content-Type': 'application/json; charset=utf-8' },
      method: 'POST',
      body: JSON.stringify({
        drink: selectedDrink,
        email: customerEmail,
        size: selectedSize
      })
    })
      .then(response => {
        if (response.status === 400) {
          // Inspect the headers in the response
          // response.headers.forEach(console.log);
          let error = new Error(response.headers.get('x-validation-error'))
          error.response = response
          throw error;
        } else {
          response.json();
        }
      }).then(data => {
        console.log('Got back', JSON.stringify(data));
      })
      .catch(e => console.log('Request failed', e));
  }


}

// Init code
window.onload = function() {
  let service = new CoffeeService();
  service.init();
};



document.getElementById('clear').onclick = function(e) {
  var queue = document.getElementById('queue-list');
  var status = document.getElementById('status-list');
  queue.innerHTML = '';
  status.innerHTML = '';
};

