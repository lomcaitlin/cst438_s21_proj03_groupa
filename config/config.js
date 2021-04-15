const dotenv = require('dotenv');
dotenv.config();
module.exports = {
  dbHost:       process.env.HOST,
  dbUser:       process.env.DBUSER,
  dbPassword:   process.env.PASSWORD,
  dbDatabase:   process.env.DATABASE,
}
