package com.cardmaster.citygraph.model;

public class Dual<T> {

	private final T leftDirection;
	private final T rightDirection;

	public Dual(T leftParam, T rightParam) {
		this.leftDirection = leftParam;
		this.rightDirection = rightParam;
	}

	public T getLeftDirection() {
		return leftDirection;
	}

	public T getRightDirection() {
		return rightDirection;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((leftDirection == null) ? 0 : leftDirection.hashCode());
		result = prime * result + ((rightDirection == null) ? 0 : rightDirection.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dual<?> other = (Dual<?>) obj;
		if (leftDirection == null) {
			if (other.leftDirection != null)
				return false;
		} else if (!leftDirection.equals(other.leftDirection))
			return false;
		if (rightDirection == null) {
			if (other.rightDirection != null)
				return false;
		} else if (!rightDirection.equals(other.rightDirection))
			return false;
		return true;
	}

}
