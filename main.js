Parse.Cloud.define('deleteUser', async(req) => {
  const userId = req.params.user;
  let query = new Parse.Query(Parse.User);
  query.get(userId).then((user) => {
    return user.destroy.then((notUser) => {

    }, (e) => {
      return e;
    });
  }, (e) => {
    return e;
  });
});