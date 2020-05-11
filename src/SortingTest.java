import javax.sound.sampled.EnumControl;
import java.io.*;
import java.util.*;

public class SortingTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			boolean isRandom = false;	// 입력받은 배열이 난수인가 아닌가?
			int[] value;	// 입력 받을 숫자들의 배열
			String nums = br.readLine();	// 첫 줄을 입력 받음
			if (nums.charAt(0) == 'r')
			{
				// 난수일 경우
				isRandom = true;	// 난수임을 표시

				String[] nums_arg = nums.split(" ");

				int numsize = Integer.parseInt(nums_arg[1]);	// 총 갯수
				int rminimum = Integer.parseInt(nums_arg[2]);	// 최소값
				int rmaximum = Integer.parseInt(nums_arg[3]);	// 최대값

				Random rand = new Random();	// 난수 인스턴스를 생성한다.

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			}
			else
			{
				// 난수가 아닐 경우
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 한줄씩 입력받아 배열원소로 대입
					value[i] = Integer.parseInt(br.readLine());
			}

			// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
			while (true)
			{
				int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.

				String command = br.readLine();

				long t = System.currentTimeMillis();
				switch (command.charAt(0))
				{
					case 'B':	// Bubble Sort
						newvalue = DoBubbleSort(newvalue);
						break;
					case 'I':	// Insertion Sort
						newvalue = DoInsertionSort(newvalue);
						break;
					case 'H':	// Heap Sort
						newvalue = DoHeapSort(newvalue);
						break;
					case 'M':	// Merge Sort
						newvalue = DoMergeSort(newvalue);
						break;
					case 'Q':	// Quick Sort
						newvalue = DoQuickSort(newvalue);
						break;
					case 'R':	// Radix Sort
						newvalue = DoRadixSort(newvalue);
						break;
					case 'X':
						return;	// 프로그램을 종료한다.
					default:
						throw new IOException("잘못된 정렬 방법을 입력했습니다.");
				}
				if (isRandom)
				{
					// 난수일 경우 수행시간을 출력한다.
					System.out.println((System.currentTimeMillis() - t) + " ms");
				}
				else
				{
					// 난수가 아닐 경우 정렬된 결과값을 출력한다.
					for (int i = 0; i < newvalue.length; i++)
					{
						System.out.println(newvalue[i]);
					}
				}

			}
		}
		catch (IOException e)
		{
			System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoBubbleSort(int[] value)
	{
		// TODO : Bubble Sort 를 구현하라.
		// value는 정렬안된 숫자들의 배열이며 value.length 는 배열의 크기가 된다.
		// 결과로 정렬된 배열은 리턴해 주어야 하며, 두가지 방법이 있으므로 잘 생각해서 사용할것.
		// 주어진 value 배열에서 안의 값만을 바꾸고 value를 다시 리턴하거나
		// 같은 크기의 새로운 배열을 만들어 그 배열을 리턴할 수도 있다.

		for(int i=0; i<value.length-1; i++) {
			for(int j=0; j<value.length-1-i; j++) {
				if(value[j]>value[j+1]) {
					int tmp = value[j];
					value[j] = value[j+1];
					value[j+1] = tmp;
				}
			}
		}

		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoInsertionSort(int[] value)
	{
		// TODO : Insertion Sort 를 구현하라.

		for(int i=1; i<value.length; i++) {
			int tmp = value[i];
			int lastJ = i;
			for(int j=i-1; j>=0; j--) {
				if(value[j]>tmp) {
					value[j+1] = value[j];
					lastJ = j;
				}
			}
			value[lastJ] = tmp;
		}

		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoHeapSort(int[] value)
	{
		// TODO : Heap Sort 를 구현하라.
		// building heap
		for(int i=value.length/2; i>=1; i--) {
			int child = 2*i;
			int rightChild = 2*i+1;
			while(child<=value.length) {
				if(rightChild<=value.length && value[child-1]<value[rightChild-1]) {
					child = rightChild; // child에 bigger value index
				}
				if(value[i-1]<value[child-1]) {
					int tmp = value[i-1];
					value[i-1] = value[child-1];
					value[child-1] = tmp;
					i = child;
					child = 2*child;
					rightChild = child+1;
				} else {
					break;
				}
			}
		}
		// sorting
		for(int i=value.length; i>=2; i--) {
			int tmp = value[0];
			value[0] = value[i-1];
			value[i-1] = tmp;

			int j=1;
			int child = 2*j;
			int rightChild = 2*j+1;
			while(child<=i-1) {
				if(rightChild<=i-1 && value[child-1]<value[rightChild-1]) {
					child = rightChild;
				}
				if(value[j-1]<value[child-1]) {
					int tmp2 = value[j-1];
					value[j-1] = value[child-1];
					value[child-1] = tmp2;
					j = child;
					child = 2*child;
					rightChild = child+1;
				} else {
					break;
				}

			}
		}


		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value)
	{
		// TODO : Merge Sort 를 구현하라.
		int[] tempArray = new int[value.length];

		if(value.length>1) {
			int[] firstArray = new int[value.length/2];
			int[] secondArray = new int[value.length - value.length/2];
			System.arraycopy(value,0,firstArray,0,value.length/2);
			System.arraycopy(value,value.length/2,secondArray,0,value.length - value.length/2);

			firstArray = DoMergeSort(firstArray);
			secondArray = DoMergeSort(secondArray);

			int i=0, j=0,k=0;
			while(true) {
				if(i==firstArray.length) {
					if(j==secondArray.length) break;
					tempArray[k++] = secondArray[j++];
				} else if(j==secondArray.length) {
					tempArray[k++] = firstArray[i++];
				} else if(firstArray[i]<secondArray[j]) {
					tempArray[k++] = firstArray[i++];
				} else tempArray[k++] = secondArray[j++];
			}


		} else {
			tempArray[0] = value[0];
		}

		return tempArray;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value)
	{
		// TODO : Quick Sort 를 구현하라.
		if(value.length>1) {
			int pivot = value[value.length-1];
			int pivotIndex = 0;
			//partition process
			for(int i=0; i<value.length-1; i++) {
				if(value[i]<pivot) {
					int tmp = value[pivotIndex];
					value[pivotIndex] = value[i];
					value[i] = tmp;
					pivotIndex++;
				}
			}
			int[] smallerArray = new int[pivotIndex];
			int[] biggerArray = new int[value.length-pivotIndex-1];
			System.arraycopy(value,0,smallerArray,0,pivotIndex);
			System.arraycopy(value,pivotIndex,biggerArray,0,value.length-pivotIndex-1);
			smallerArray = DoQuickSort(smallerArray);
			biggerArray = DoQuickSort(biggerArray);
			System.arraycopy(smallerArray,0,value,0,smallerArray.length);
			value[smallerArray.length] = pivot;
			System.arraycopy(biggerArray,0,value,smallerArray.length+1,biggerArray.length);

		}

		return value;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value)
	{
		// TODO : Radix Sort 를 구현하라.
		int maxAbs = value[0];
		for(int i=1; i<value.length; i++) {
			if(Math.abs(value[i])>maxAbs) maxAbs = Math.abs(value[i]);
		}
		int d = (int)Math.floor(Math.log10(maxAbs))+1;
		// 최대 자리수를 찾는 과정 빅세타(n)

		for(int i=0; i<d; i++) {
			MyQueue[] myQueues = new MyQueue[19];
			for(int m=0; m<myQueues.length; m++) {
				myQueues[m] = new MyQueue();
			}
			for(int j=0; j<value.length; j++) {
				int number = (value[j]/((int)Math.pow(10,i)))%10 + 9;
				myQueues[number].add(value[j]);
			}
			int count=0;
			for(int k=0; k<myQueues.length; k++) {
				while(!myQueues[k].isEmpty()) {
					value[count++] = myQueues[k].poll();
				}
			}
		}

		return (value);
	}
}

interface MyQueueInterface {
	public int size();
	public boolean isEmpty();
	public int poll();
	public int peek();
	public void add(int num);
}

class MyNode {
	private int value;
	private MyNode nextNode;
	MyNode(int value){
		this.value = value;
	}
	public void setNext(MyNode nextNode) {
		this.nextNode = nextNode;
	}
	public MyNode getNext() {
		return nextNode;
	}
	public int getValue() {
		return value;
	}

}

class MyQueue implements MyQueueInterface {
	MyNode head = new MyNode(0);
	@Override
	public int size() {
		MyNode first = head;
		int count=0;
		while(first.getNext()!=null) {
			first = first.getNext();
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		if(size()==0) return true;
		else return false;
	}

	@Override
	public int poll() {
		int tmp = head.getNext().getValue();
		head.setNext(head.getNext().getNext());
		return tmp;
	}

	@Override
	public int peek() {
		return head.getNext().getValue();
	}

	@Override
	public void add(int num) {
		MyNode lastNode = head;
		for(int i=0; i<size(); i++) {
			lastNode = lastNode.getNext();
		}
		lastNode.setNext(new MyNode(num));
	}
}