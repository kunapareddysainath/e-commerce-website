import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom';
import Navbar from '../components/Navbar'
import { Footer } from '../components';
import axios from "axios";
import { backEndUrl } from '../config';

const Orders = () => {
    const [orders, setOrders] = useState([]);

    const EmptyOrders = () => {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-md-12 py-5 bg-light text-center">
                        <h4 className="p-3 display-5">No orders Placed</h4>
                        <Link to="/" className="btn  btn-outline-dark mx-4">
                            <i className="fa fa-arrow-left"></i>
                            Continue Shopping
                        </Link>
                    </div>
                </div>
            </div>
        );
    };

    const fetchOrders = async () => {

        const authToken = localStorage.getItem("authToken");

        try {
            const response = await axios.get(
                backEndUrl + `/api/order/getOrdersByUser`
                ,
                {
                    headers: {
                        'Authorization': `Bearer ${authToken}` // Include authToken in headers
                    }
                }
            );
            setOrders(response.data);
            // Handle the response if needed
        } catch (error) {
            // Handle the error if needed
        }
    };

    const ShowOrders = () => {

        const formatPaymentMethod = (paymentMethod) => {
            // Convert to lowercase
            paymentMethod = paymentMethod.toLowerCase();

            // Replace underscores with spaces
            paymentMethod = paymentMethod.replace(/_/g, ' ');

            // Capitalize the first letter of each word
            paymentMethod = paymentMethod.replace(/\b\w/g, function (char) {
                return char.toUpperCase();
            });

            return paymentMethod;
        }

        return (
            <>
                {orders.length > 0 && (orders.map((order, index) => (
                    <div key={index} className="col-12">
                        <div className="card mb-4">
                            <div className="card-header py-3 bg-light">
                                <h5 className="mb-0"> Order Number: {order.orderNumber}</h5>
                            </div>
                            <div className="card-body">
                                <ul className="list-group list-group-flush">
                                    <li className="list-group-item d-flex justify-content-between align-items-center px-0">
                                        Order Status: <span>{order.orderStatus}</span>
                                    </li>
                                    <li className="list-group-item d-flex justify-content-between align-items-center px-0">
                                        Payment Method: <span>{formatPaymentMethod(order.paymentMethod)}</span>
                                    </li>
                                    <li className="list-group-item d-flex justify-content-between align-items-center px-0">
                                        Payment status: <span>{order.paymentStatus}</span>
                                    </li>
                                    <li className="list-group-item d-flex flex-column flex-md-row justify-content-between align-items-start px-0">
                                        <span className="font-weight">Shipping Address:</span>
                                        <span className="text-md-right text-wrap">
                                            {order.shippingAddress.address1}, {order.shippingAddress.address2}, {order.shippingAddress.state}, {order.shippingAddress.country}-{order.shippingAddress.zip}
                                        </span>
                                    </li>

                                    <li className="list-group-item d-flex justify-content-between align-items-center px-0">
                                        Order Items:
                                    </li>
                                    <li className='list-group-item'>
                                        <div className="d-flex flex-row flex-wrap justify-content-center">
                                            {order.orderItemList.map((item) => (
                                                <div key={item.id} className="card m-2" style={{ width: '150px' }}>
                                                    <div className="d-flex justify-content-center align-items-center" style={{ height: "150px" }}>
                                                        <img
                                                            src={item.imageUrl}
                                                            alt="Card"
                                                            className='py-4'
                                                            width={100}
                                                            style={{ maxHeight: "120px", maxWidth: "100%" }}
                                                        />
                                                    </div>
                                                    <div className="card-body text-center">
                                                        <h6 className="card-title">
                                                            {item.product_name.substring(0, 12)}
                                                        </h6>
                                                    </div>
                                                    <p className="card-body text-center">₹{item.price}</p>
                                                </div>
                                            ))}
                                        </div>
                                    </li>
                                    <li className="list-group-item d-flex justify-content-between align-items-center px-0">
                                        Total: <span>{order.totalAmount}</span>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>)))}
            </>
        )
    };

    useEffect(() => {
        fetchOrders();
    }, []);

    return (
        <>
            <Navbar />
            <h1 className="text-center">Orders</h1>
            <hr />
            {orders.length > 0 ? <ShowOrders /> : <EmptyOrders />}
            <Footer />
        </>
    )
}

export default Orders;
