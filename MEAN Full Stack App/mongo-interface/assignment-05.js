const MongoClient = require('mongodb').MongoClient;
const ObjectId = require('mongodb').ObjectId;
const Fakerator = require('fakerator');
const fakerator = Fakerator('en-UK');
const url = "mongodb+srv://demonstrator:demopass@cluster0-6dlry.azure.mongodb.net/test?retryWrites=true&w=majority";

if(process.argv[2] === '-help'){
    console.log('Commands: '+
        '\n-add customer | phone | order'+
        '\n-update customer | phone | order'+
        '\n-find customer | phone | order'+
        '\n-remove customer email phone name'+
        '\n-remove phone manufacturer model'+
        '\n-remove order customerId'+
        '\nSubmission by #18413532');
    return;
}

MongoClient.connect(url, {poolSize: 10, useUnifiedTopology: true}, function (err, db) {
    if (err) throw err;
    console.log("Connected.");
    const dbo = db.db('mydb');

    performOperations();

    /**
     * Handles CRUD operations for customers, phones and orders.
     * @returns {Promise<void>}
     */
    async function performOperations(){
        switch(process.argv[2]){
            case '-add':
                switch(process.argv[3]) {
                    case 'customer':
                        addCustomer();
                        break;
                    case 'phone':
                        addPhone();
                        break;
                    case 'order':
                        placeOrder();
                        break;
                }
                break;
            case '-remove':
                switch(process.argv[3]) {
                    case 'customer':
                        const email = process.argv[4];
                        const phone = process.argv[5];
                        process.argv.splice(0, 6);
                        const name = process.argv.join(' ');
                        deleteCustomer(email, phone, name);
                        break;
                    case 'phone':
                        const manufacturer = process.argv[4];
                        const model = process.argv[5];
                        deletePhone(manufacturer, model);
                        break;
                    case 'order':
                        const id = process.argv[4]
                        deleteOrder(id);
                        break;
                }
                break;
            case '-update':
                switch(process.argv[3]){
                    case 'customer':
                        updateCustomer();
                        break;
                    case 'phone':
                        updatePhone();
                        break;
                    case 'order':
                        updateOrder();
                        break;
                }
                break;
            case '-find':
                switch(process.argv[3]){
                    case 'customer':
                        findCustomer();
                        break;
                    case 'phone':
                        findPhone();
                        break;
                    case 'order':
                        findOrder();
                        break;
                }
                break;
        }
        await sleep(2000);
        db.close();

        function sleep(ms) {    //Sleep in JavaScript best practice: https://stackoverflow.com/a/39914235
            return new Promise(resolve => setTimeout(resolve, ms));
        }
    }

    /**
     * Add a customer with random details to the 'customers' collection.
     */
    function addCustomer() {
        dbo.collection('customers').insertOne(generateCustomer(), (err, res) => {
            if (err) throw err;
            console.log('Customer added: ' + res.insertedId.toString());
        });
    }

    /**
     * Find a random customer and print their details in a formatted manner.
     * @return {Promise} customer ID string
     */
    function findCustomer() {
        return new Promise ((res) => {
            const query = [{$sample: {size: 1}}];
            dbo.collection('customers').aggregate(query, (err, cursor) => {
                cursor.toArray((err, docs) => {
                    if (err) throw err;
                    console.log(`Customer details: \n${docs[0]._id}\n${docs[0].firstName} ${docs[0].lastName} ${docs[0].mobile} ${docs[0].email}\n` +
                        `Address: \n${docs[0].address.line_one} ${docs[0].address.line_two}`);
                    res(docs[0]._id);
                });
            });
        });
    }

    /**
     * Deletes a customer from the 'customers' collection, given their email, phone and full name for confirmation.
     * @param email
     * @param phone
     * @param name
     */
    function deleteCustomer(email, phone, name) {
        name = name.split(' ');
        const query = {
            firstName: name[0],
            lastName: name[1],
            mobile: phone,
            email: email
        };
        dbo.collection('customers').deleteOne(query, (err, res) => {
            if (err) throw err;
            console.log(res.deletedCount + ' deleted.');
        });
    }

    /**
     * Selects the first customer in the collection and modifies it
     * to show the update operation was performed successfully.
     */
    function updateCustomer() {
        const update = {
            $set: {
                phone: '083-999-9999',
                email: 'updated@email.com',
                address: {
                    line_one: 'Updated line_one',
                    line_two: 'Updated line_two'
                }
            }
        };
        dbo.collection('customers').updateOne({}, update, (err, res) => {
            console.log(res.modifiedCount + ' modified.');
        });
    }

    /**
     * Adds a new entry to the 'phones' collection.
     */
    function addPhone() {
        dbo.collection('phones').insertOne(generatePhone(), (err, res) => {
            if (err) throw err;
            console.log('Phone added: ' + res.insertedId.toString());
        });
    }

    /**
     * Selects a random phone in the 'phones' collection and prints its details in a formatted manner.
     * @return {Promise} phone ID
     */
    function findPhone() {
        return new Promise((res) => {
            const query = [{$sample: {size: 1}}];
            dbo.collection('phones').aggregate(query, (err, cursor) => {
                cursor.toArray((err, docs) => {
                    console.log(`Phone details: \n${docs[0]._id}\n${docs[0].manufacturer} ${docs[0].model}: ${docs[0].price}`);
                    res(docs[0]._id);
                });
            });
        });
    }

    /**
     * Finds and removes a phone, given its manufacturer and model as input.
     * Example: Apple iPhone
     * @param {string} manufacturer
     * @param {string} model
     */
    function deletePhone(manufacturer, model) {
        const query = {
            manufacturer: manufacturer,
            model: model
        };
        dbo.collection('phones').deleteOne(query, (err) => {
            if (err) throw err;
            console.log('Phone deleted successfully.');
        });
    }

    /**
     * Sets a random phone's details (manufacturer, model) to "Updated Phone"
     * to show the update was clearly performed successfully.
     */
    function updatePhone() {
        const update = {
            $set: {
                manufacturer: 'Updated',
                model: 'Phone'
            }
        };
        dbo.collection('phones').updateOne({}, update, (err, res) => {
            console.log(res.modifiedCount + ' modified.');
        });
    }

    /**
     * Adds a new random phone ID to the 'orders' set of a random customer from 'customers'.
     */
    async function placeOrder() {
        const query = { _id: await findCustomer() };                       //Querying by _id:
        const update = { $push: { orders: await findPhone() } };           //https://stackoverflow.com/a/32170615
        await dbo.collection('customers').updateOne(query, update, (err, res) => {
            if (err) throw err;
            console.log('Order placed: ' + res.modifiedCount);
        });
    }

    /**
     * Finds a random order and outputs its details in a formatted manner.
     */
    function findOrder() {
        const query = [{$sample: {size: 1}}];
        dbo.collection('customers').aggregate(query, (err, cursor) => {
            cursor.toArray((err, docs) => {
                console.log(`Order details: \nCustomer ID: ${docs[0]._id} ordered phone IDs:`);
                if(docs[0].orders.length === 0)
                    console.log('This customer has not ordered anything.');
                docs[0].orders.forEach(item => {
                    console.log(`${item}`);
                });
            });
        });
    }

    /**
     * Updates a random customer with only one order in the collection
     * and overwrites it, leaving an updated message.
     */
    function updateOrder() {
        const query = { orders : { $size : 1 } };
        const update = { $set: { orders : ['Order cleared, this is an updated order'] } };
        dbo.collection('customers').updateOne(query, update, (err, res) => {
            if (err) throw err;
            console.log(res.modifiedCount + ' modified.');
        });
    }

    /**
     * Deletes one order matching a customer's ID by popping it off the customer's orders list.
     * @param {string} customerId
     */
    function deleteOrder(customerId) {
        const query = {
            _id: ObjectId(customerId)
        };
        const update = { $pop: { orders: -1 } }
        dbo.collection('customers').updateOne(query, update, (err, res) => {
            if (err) throw err;
            console.log('Orders deleted: ' + res.modifiedCount);
        });
    }

    /**
     * Generates a customer object to be inserted into the 'customers' collection.
     * @returns {{firstName: *, lastName: *, address: {line_one: *, eircode: *, line_two: *}, mobile: *, title: string, email: *}}
     */
    function generateCustomer() {
        const titles = ['Mx', 'Ms', 'Mr', 'Mrs', 'Miss', 'Dr'];
        return {
            title: titles[Math.floor(Math.random() * titles.length)],
            firstName: fakerator.names.firstName(),
            lastName: fakerator.names.lastName(),
            mobile: fakerator.phone.number(),
            email: fakerator.internet.email(),
            address: {
                line_one: fakerator.address.street(),
                line_two: fakerator.address.city(),
                eircode: fakerator.address.postCode()
            },
            orders: [] //Stores phone IDs
        };
    }

    /**
     * Generates a phone object to be inserted into the 'phones' collection.
     * @returns {{price: number, model: (string), manufacturer: (string)}}
     */
    function generatePhone() {
        const manufacturers = ['Apple', 'Samsung', 'Huawei', 'Google', 'HTC'];
        const model = ['Coolphone', 'Bestphone', 'Bettererphone', 'Betterphone'];
        return {
            manufacturer: manufacturers[Math.floor(Math.random() * manufacturers.length)],
            model: model[Math.floor(Math.random() * model.length)],
            price: Math.round(Math.random() * 500 + 600)
        };
    }
});

/*
Mandatory reflection and retrospect:

NoSQL is infinitely more intuitive to me compared to MySQL.
I found the embedded address information within the customer object to be easier to grasp.

I managed to embed order information into the customers object as a list of phone IDs,
since I identified the one-to-many relationship between customers and orders.
 */