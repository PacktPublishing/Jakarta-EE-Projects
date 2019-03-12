var source = new EventSource('http://localhost:8030/coffee-orders/resources/order-events');
// source.onopen = function(event) {
//   console.log('Opened event connection', event);
// };
source.addEventListener(
  'order',
  e => {
    append(e.data);
  },
  false
);
// source.onmessage = function(event) {
//   append(event.data);
// };
// source.onerror = function(event) {
//   console.log(event);
// };
function append(data) {
  console.log('Order Event', data);
  const orderInfo = JSON.parse(data);

  var queue = document.getElementById('queue-list');
  var status = document.getElementById('status-list');

  var li = document.createElement('li');
  var p = document.createElement('p');
  var aLink = document.createElement('a');
  aLink.setAttribute('target', '_blank');
  aLink.setAttribute(
    'href',
    `http://localhost:8030/coffee-orders/resources/orders/${orderInfo.id}`
  );
  aLink.innerHTML = orderInfo.id;

  li.setAttribute('id', orderInfo.id);
  p.appendChild(aLink);
  li.appendChild(p);
  li.appendChild(document.createTextNode(`${orderInfo.customerEmail}  ${orderInfo.drink}`));

  if (orderInfo.status === 'ACCEPTED') {
    queue.insertBefore(li, queue.childNodes[0]);
  } else {
    status.insertBefore(li, status.childNodes[0]);
  }
}