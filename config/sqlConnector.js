const mysql = require('mysql');
const util = require('util');
const { dbHost, dbUser, dbPassword, dbDatabase } = require('./config');

let pool = mysql.createPool({
    connectionLimit : 50,
    host     : dbHost,
    user     : dbUser,
    password : dbPassword,
    database : dbDatabase,
    debug    :  false
});

pool.query = util.promisify(pool.query);

module.exports = pool;
