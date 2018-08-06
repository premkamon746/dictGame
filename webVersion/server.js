const express = require('express');
const app = express();

app.listen(8000,()=>{
	console.log('Server started!');
})

const bodyParser = require('body-parser');
app.use(bodyParser.json());
app.route('/api/cats/:name').put((req, res) => {
  res.send(200, req.body);
});