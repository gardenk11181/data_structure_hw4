import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class SortingTest2
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			int[] value;	// 입력 받을 숫자들의 배열
			String nums = br.readLine();	// 첫 줄을 입력 받음

			String[] nums_arg = nums.split(" ");

			int numsize = Integer.parseInt(nums_arg[0]);	// 총 갯수
			int rminimum = Integer.parseInt(nums_arg[1]);	// 최소값
			int rmaximum = Integer.parseInt(nums_arg[2]);	// 최대값


			for(int k=0; k<100; k++) { //100번 반
				Random rand = new Random();	// 난수 인스턴스를 생성한다.

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;


				// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.

				int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.

				long t = System.currentTimeMillis();
				newvalue = DoQuickSort(newvalue);

				//수행시간을 출력한다.
				System.out.println((System.currentTimeMillis() - t));
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
	private static int[] percolateDown(int[] value,int parent, int end) {
		int child = 2*parent;
		int rightChild = 2*parent+1;
		if(child<=end) {
			if(rightChild<=end && value[child-1]<value[rightChild-1]) {
				child = rightChild;
			}
			if(value[parent-1]<value[child-1]) {
				int tmp = value[child-1];
				value[child-1] = value[parent-1];
				value[parent-1] = tmp;
				return percolateDown(value,child,end);
			}
		}
		return value;
	}

	private static int[] DoHeapSort(int[] value)
	{
		// TODO : Heap Sort 를 구현하라.
		// building heap
		for(int i=value.length/2; i>=1; i--) {
			value = percolateDown(value,i,value.length);
		}
		// sorting
		for(int i=value.length; i>=2; i--) {
			int tmp = value[0];
			value[0] = value[i-1];
			value[i-1] = tmp;
			value = percolateDown(value,1,i-1);
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
		// 2D int array
		for(int i=0; i<d; i++) {
			int[][] numbers = new int[19][value.length+1];
			for(int j=0; j<value.length; j++) {
				int number = (value[j]/((int)Math.pow(10,i)))%10 + 9;
				int lastIndex = numbers[number][value.length];
				numbers[number][lastIndex] = value[j];
				numbers[number][value.length]++;
			}
			int count=0;
			for(int k=0; k<19; k++) {
				for(int m=0; m<numbers[k][value.length]; m++) {
					value[count++] = numbers[k][m];
				}
			}
		}

		return (value);
	}
}

