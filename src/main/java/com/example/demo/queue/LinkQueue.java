package com.example.demo.queue;

import com.example.demo.entity.Node;

public class LinkQueue<T> {

	// 队头
	private Node<T> front;
	// 队尾
	private Node<T> rear;
	// 元素个数
	private int size;

	/**
	 * 创建队列
	 */
	public LinkQueue() {
		rear = front = null;
	}

	/**
	 * 入队列
	 * 
	 * @param data
	 */
	public void enQueue(T data) {
		Node<T> node = new Node<T>(data);
		if (isEmputy()) {
			front = rear = node;
		} else {
			rear.setNext(node);
			rear = node;
		}

		size++;
	}

	/**
	 * 出队列
	 * 
	 * @return 返回数据
	 */
	public synchronized T deQueue() {
		if (isEmputy()) {
			throw new RuntimeException("队列为空");
		}

		Node<T> delete = front;
		front = delete.getNext();
		delete.setNext(null);
		; // help GC
		size--;

		if (size == 0) {
			// 删除掉最后一个元素时，front值已经为null，但rear还是指向该节点，需要将rear置为null
			// 最后一个结点front和rear两个引用都没指向它，帮助GC处理该节点对象
			rear = front;
		}

		return (T) delete.getData();
	}

	/**
	 * 判断队列是否为空
	 * 
	 * @return
	 */
	public boolean isEmputy() {
		return (front == null && rear == null) ? true : false;
	}

	/**
	 * 获取队列的元素个数
	 * 
	 * @return
	 */
	public int size() {
		return this.size;
	}

}
