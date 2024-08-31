import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { backEndUrl } from '../config';
import Navbar from '../admin-components/Navbar';
import SideMenu from '../admin-components/SideMenu';
import { Footer } from '../components';

const Orders = () => {
  const [orders, setOrders] = useState([]);

  const fetchOrders = async () => {
    const authToken = localStorage.getItem("authToken");

    try {
      const response = await axios.get(
        `${backEndUrl}/api/order/getAllOrders`,
        {
          headers: {
            'Authorization': `Bearer ${authToken}`
          }
        }
      );

      setOrders(response.data);
      console.log(response.data);
    } catch (error) {
      console.error("Error fetching orders:", error);
    }
  };

  useEffect(() => {
    fetchOrders();
  }, []);

  const handleUpdateOrderStatus = async (order) => {
    try {
      const authToken = localStorage.getItem("authToken");
      await axios.post(
        `${backEndUrl}/api/order/updateOrderStatus`,
        order,
        {
          headers: {
            'Authorization': `Bearer ${authToken}`
          }
        }
      );
      fetchOrders();
    } catch (error) {
      console.error("Error updating order status:", error);
    }
  };

  const formatDate = (isoDate) => {
    const date = new Date(isoDate);
    const day = String(date.getUTCDate()).padStart(2, '0');
    const month = String(date.getUTCMonth() + 1).padStart(2, '0');
    const year = date.getUTCFullYear();
    return `${day}-${month}-${year}`;
  };

  return (
    <>
      <Navbar />
      <div className='container-fluid'>
        <div className='row'>
          <div className="col-md-2">
            <SideMenu />
          </div>
          <div className="col-md-10">
            <div className="container mt-5">
              <div className="card">
                <div className="card-header">
                  <h3>Order List</h3>
                </div>
                <div className="card-body">
                  <table className="table table-striped">
                    <thead>
                      <tr>
                        <th>Order Number</th>
                        <th>Customer Name</th>
                        <th>Order Status</th>
                        <th>Payment Status</th>
                        <th>Total Amount</th>
                        <th>Order Date</th>
                        <th>Actions</th>
                      </tr>
                    </thead>
                    <tbody>
                      {orders.map((order, index) => (
                        <tr key={index}>
                          <td>{order.orderNumber}</td>
                          <td>{order.customer.name}</td>
                          <td>{order.orderStatus}</td>
                          <td>{order.paymentStatus}</td>
                          <td>{order.totalAmount}</td>
                          <td>{formatDate(order.orderCreatedAt)}</td>
                          <td>
                          {order.orderStatus === 'Accepted' ? (
  <span className="text-success">Accepted</span>
) : order.orderStatus === 'Cancelled' ? (
  <span className="text-danger">Cancelled</span>
) : order.orderStatus === 'Delivered' ? (
  <span className="text-primary">Delivered</span>
) : (
  <>
    <button
      className="btn btn-success mr-2"
      onClick={() => handleUpdateOrderStatus({
        orderId: order.orderId,
        orderStatus: "Accepted"
      })}
    >
      Accept
    </button>
    <button
      className="btn btn-danger"
      onClick={() => handleUpdateOrderStatus({
        orderId: order.orderId,
        orderStatus: "Cancelled"
      })}
    >
      Cancel
    </button>
  </>
)}

                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default Orders;
