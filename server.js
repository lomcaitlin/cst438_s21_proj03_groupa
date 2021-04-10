const express = require('express');

const app = express();

app.set('port', process.env.PORT || 3000);
const server = app.listen(app.get('port'), () => {
    console.log('Express server listening on port ' + server.address().port);
});

app.get("/", (req, res) => {
    res.send({"hello": "world"});
});
