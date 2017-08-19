//https://nodejs.org/api/os.html#os_os_networkinterfaces
var os = require('os');

var interfaces = os.networkInterfaces();

var addresses = [];
for (var k in interfaces) {
    for (var k2 in interfaces[k]) {
        var address = interfaces[k][k2];
        // console.log({
        //   intr: k,
        //   address: address.address
        // })
        if (address.family === 'IPv4' && !address.internal) {
            addresses.push({
              intr: k,
              address: address.address
            });
        }
    }
}

addresses.sort((a, b)=>{
  if (a.intr.match(/eth/) !== null) return -1;
  if (a.intr.match(/en/) !== null) return -1;
  return 1;
})

var result = (addresses.length > 0) ? addresses[0].address:'';
console.log('ip address', result);

exports.ip = result;
