import interaction.UserInteraction;

public class Main {
	public static void main(String[] args) {
		UserInteraction ui = new UserInteraction();
		while(!ui.uiChooseInputStream());
		int n;
		while (true) {
			try {
				ui.printLine("Введите размерность матрицы: (<=20)");
				n = Integer.parseInt(ui.read());
				if (n > 20) {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException e) {
				if(!ui.isConsoleInput()) {
					ui.printLine("Ошибка входных данных, завершение работы программы.");
					return;
				}
				ui.printLine("Введите число! (<=20)");
				continue;
			}
			break;
		}
		double[][] Ast = new double[100][100], A = new double[100][100];    // создаем две матрицы, Ast для вычисления невязок
		double[] bst = new double[100], b = new double[100];    // создаем два B столбца, bst для вычисления невязок
		for (int i = 0; i < n; i++) {
			A[i] = new double[100];
			for (int j = 0; j < n; j++) {
				A[i][j] = Double.parseDouble(ui.read());    // вводим из файла матрицу
				Ast[i][j] = A[i][j];
			}
			b[i] = Double.parseDouble(ui.read());
			bst[i] = b[i];
		}

		// метод Гаусса
		double det = 1;
		for (int p = 0; p < n; p++) {

			int max = p;
			for (int i = p + 1; i < n; i++) {
				if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {    // ищем максимум в столбце, чтобы
					max = i;                                        // потом свапнуть его
				}
			}
			double[] temp = A[p]; A[p] = A[max]; A[max] = temp;   // меняем местами строчки матрицы
			double   t    = b[p]; b[p] = b[max]; b[max] = t;      // и столбец b тоже

			if (Math.abs(A[p][p]) <= 1e-10) {     // если получили 0 на главной диагонали, то сворачиваемся
				System.out.println("Нет решения.");
				return;
			} else {
				det *= A[p][p];   // считаем определитель по главной диагонали
			}
			System.out.println("!!!!!!!!!!!!!!!!!!!!!");
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					System.out.print(A[i][j] + " ");
				}
				System.out.println(b[i]);
			}
			System.out.println("!!!!!!!!!!!!!!!!!!!!!");

			for (int i = p + 1; i < n; i++) { // приводим матрицу к треугольному виду
				double alpha = A[i][p] / A[p][p];
				b[i] -= alpha * b[p];
				for (int j = p; j < n; j++) {
					A[i][j] -= alpha * A[p][j];
				}
			}
			System.out.println("-----------------------");
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					System.out.print(A[i][j] + " ");
				}
				System.out.println(b[i]);
			}
			System.out.println("-----------------------");
		}


		// обратный проход
		double[] x = new double[n];
		for (int i = n - 1; i >= 0; i--) {  // идем с конца матрицы и находим неизвестные, начиная с последнего
			double sum = 0.0;
			for (int j = i + 1; j < n; j++) {
				sum += A[i][j] * x[j];   // считаем сумму элементов в строке в верхней половине матрицы
			}
			x[i] = (b[i] - sum) / A[i][i];    // считаем неизвестную, вычитая сумму из b-го элемента
		}                                   //   и деля на диагональный элемент

		// выводим результат
		System.out.println("Determinant = " + det);

		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				System.out.print(A[i][j] + " ");
			}
			System.out.println(b[i]);
		}

		System.out.println();
		System.out.println("Неизвестные: ");
		for (int i = 0; i < n; i++) {
			System.out.print(x[i] + " ");
		}

		System.out.println();
		System.out.println("Невязки: ");

		for(int i = 0; i < n; i++) {
			double sum = 0.0;
			for(int j = 0; j < n; j++) {
				sum += Ast[i][j] * x[j];
			}
			System.out.print(sum - bst[i] + " ");
		}
	}
}
