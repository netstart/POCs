var myItems = [
  { name: 'Banana', price: 5 },
  { name: 'Nutella', price: 10 },
  { name: 'Leite', price: 2 },
  { name: 'Café', price: 5 }
];

var GroceryItem = React.createClass({
  render: function() {
      return (
        <li className='grocery-list-item'>
          <span className='name'>{this.props.name}</span>
          <span className='price'>R${this.props.price}</span>
        </li>
    );
  }
});

var GroceryList = React.createClass({
    render: function() {
      var items = this.props.items.map(function(item, i) {
        return (
            <GroceryItem name={item.name} price={item.price} />
        );
      });

      return (
          <div className='grocery-list'>
          <h1 className='title'>{this.props.name}</h1>
            <ul className='grocery-list'>
             {items}
            </ul>
          </div>
      );
    }
});

React.render(
  <GroceryList name='My Grocery List' items={myItems} />, document.querySelector('#content')
);
