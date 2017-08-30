package omniaTRS;

import Jama.Matrix;

public class LogisticRegression {
  
  /**
   * number of discrete values of y
   */
  private int J;
  /**
   * number of populations
   */
  private int N;
  /**
   * number of columns in x
   */
  private int K; 
  
  private double[][] y;
  private double[][] x;
  private double[] n;
  private double[] xrange;
  
  private int i,j,k;
  private final int max_iter = 30;
  private final double eps = 1e-8;
  private int iter = 0;
  private boolean converged = false;
  private double loglike = 0;
  private double loglike_old = 0;
  private double[][] xtwx;
  private double[] beta;
  private double[] beta_inf;
  private double[] beta_old;
  private double[][] pi;
  private double R_2 = -1;
  
  private Gamma gamma = new Gamma();
  
  public LogisticRegression(int J, int N, int K, double[][] y, double[][] x, double[] n, double[] xrange)
  {
    this.J=J;
    this.N=N;
    this.K=K;
    xtwx = new double[(K+1)*(J-1)][(K+1)*(J-1)];
    beta = new double[(K+1)*(J-1)];
    beta_inf = new double[(K+1)*(J-1)];
    beta_old = new double[(K+1)*(J-1)];
    pi = new double[N][J-1];
    this.y=y;
    this.x = x;
    this.n=n;
    this.xrange = xrange;
    initializeTable();
  }

  private void initializeTable() {
    for (k = 0; k < (K + 1) * (J - 1); k++) {
      beta[k] = 0;
      beta_inf[k] = 0;
      for (j = 0; j < (K + 1) * (J - 1); j++) {
      xtwx[k][j] = 0;
      }    
    }
  }
  
  public void compute()
  {
    R_2=-1;
    while (iter < max_iter && !converged) {
      
      /* copy beta to beta_old */
      for (k = 0; k < (K + 1) * (J - 1); k++) {
      beta_old[k] = beta[k];
      }
      
      /* run one iteration of newton_raphson */
      loglike_old = loglike;
      loglike = newton_raphson();
      
      /* test for infinite parameters */
      for (k = 0; k < (K + 1) * (J - 1); k++) {
      if (beta_inf[k] != 0) {
      beta[k] = beta_inf[k];
      }
      else {
      if ((Math.abs(beta[k]) > (5 / xrange[k])) && (Math.sqrt(xtwx[k][k]) >= (3 * Math.abs(beta[k])))) {
        beta_inf[k] = beta[k];
      }
      }
      }
      
      /* test for convergence */
      converged = true;
      for (k = 0; k < (K + 1) * (J - 1); k++) {
      if (Math.abs(beta[k] - beta_old[k]) >
      eps * Math.abs(beta_old[k])) {
      converged = false;
      break;
      }
      }
      iter++;
      System.out.println("Iteracja: "+ iter);
            
    }
  }

  private double newton_raphson() {
    int i, j, jj, jprime, k, kk, kprime;
    double denom;
    double tmp1, tmp2, w1, w2;
    double[] numer = new double[J-1];
    double[] beta_tmp = new double[(K+1)*(J-1)];
    double[][] xtwx_tmp = new double[(K+1)*(J-1)][(K+1)*(J-1)];
    double loglike_tmp = 0;
    
    /* main loop for each row in the design matrix */
    for (i = 0; i < N; i++) {
    /* matrix multiply one row of x * beta */
    denom = 1;
    for (j = 0; j < J - 1; j++) {
    tmp1 = 0;
    for (k = 0; k < K + 1; k++) {
    tmp1 += x[i][k] * beta[j*(K+1)+k];
    }
    numer[j] = Math.exp(tmp1);
    denom += numer[j];
    }
    /* calculate predicted probabilities */
    for (j = 0; j < J - 1; j++) {
    pi[i][j] = numer[j] / denom;
    }
    
    /* add log likelihood for current row */
    loglike_tmp += gamma.log_gamma(n[i] + 1);
    for (j = 0, tmp1 = 0, tmp2 = 0; j < J - 1; j++) {
    tmp1 += y[i][j];
    tmp2 += pi[i][j];
    loglike_tmp = loglike_tmp - gamma.log_gamma(y[i][j]+1) +
    y[i][j] * Math.log(pi[i][j]);
    }
    /* Jth category */
    loglike_tmp = loglike_tmp - gamma.log_gamma(n[i]-tmp1+1) +
    (n[i]-tmp1) * Math.log(1-tmp2);
    /* add first and second derivatives */
    for (j = 0, jj = 0; j < J - 1; j++) {
    tmp1 = y[i][j] - n[i] * pi[i][j];
    w1 = n[i] * pi[i][j] * (1 - pi[i][j]);
    for (k = 0; k < K + 1; k++) {
    beta_tmp[jj] += tmp1 * x[i][k];
    kk = jj - 1;
    for (kprime = k; kprime < K + 1; kprime++) {
    kk++;
    xtwx_tmp[jj][kk] +=
    w1 * x[i][k] * x[i][kprime];
    xtwx_tmp[kk][jj] = xtwx_tmp[jj][kk];
    }
    for (jprime = j + 1; jprime < J - 1; jprime++) {
    w2 = -n[i] * pi[i][j] * pi[i][jprime];
    for (kprime = 0; kprime < K + 1; kprime++) {
    kk++;
    xtwx_tmp[jj][kk] +=
    w2 * x[i][k] * x[i][kprime];
    xtwx_tmp[kk][jj] = xtwx_tmp[jj][kk];
    }
    }
    jj++;
    }
    }   
  }
    /* compute xtwx * beta(0) + x(y-mu) */
    for (i = 0; i < (K + 1) * (J - 1); i++) {
    tmp1 = 0;
    for (j = 0; j < (K + 1) * (J - 1); j++) {
    tmp1 += xtwx_tmp[i][j] * beta[j];
    }
    beta_tmp[i] += tmp1;
    }
    Matrix a = new Matrix(xtwx_tmp);
    Matrix b = a.inverse();
    xtwx = b.getArrayCopy();
    /* solve for new betas */
    for (i = 0; i < (K + 1) * (J - 1); i++) {
    tmp1 = 0;
    for (j = 0; j<(K + 1) * (J - 1); j++) {
    tmp1 += xtwx[i][j] * beta_tmp[j];
    }
    beta[i] = tmp1;
    }
    return loglike_tmp;
  }
    
    
  
  private class Gamma {
    
    public Gamma(){};

    public double log_gamma(double x) {
       double tmp = (x - 0.5) * Math.log(x + 4.5) - (x + 4.5);
       double ser = 1.0 + 76.18009173    / (x + 0)   - 86.50532033    / (x + 1)
                        + 24.01409822    / (x + 2)   -  1.231739516   / (x + 3)
                        +  0.00120858003 / (x + 4)   -  0.00000536382 / (x + 5);
       return tmp + Math.log(ser * Math.sqrt(2 * Math.PI));
    }
    
    public double gamma(double x) { return Math.exp(log_gamma(x)); }

    
 }
  

  public double[] getBeta()
  {
    return this.beta;
  }
  
  public double[][] getProbabilities()
  {
    return this.pi;
  }
  
  /**
   * Metoda zwraca tablicê trafnoœci prognoz ex post
   * [0] - empiryczne 1 i prognozowane 1
   * [1] - empiryczne 1 i prognozowane 0
   * [2] - empiryczne 0 i prognozowane 1
   * [3] - empiryczne 0 i prognozowane 0
   * @return tablica trafnoœci
   */
  public int[] getExPostTable()
  {
    int[] exPostTable = {0, 0, 0, 0};

    int numberOfSuccess = 0;
  
    for(int i=0;i<y.length; i++)
    {
      numberOfSuccess+=y[i][0];
    }
    double rateOfSuccess = numberOfSuccess/y.length;
    double p;
    int forecast;
    for(int i=0; i<x.length; i++)
    {
      p = 1*beta[0] + x[i][1]*beta[1];
      if(p>=rateOfSuccess)
      {
        forecast = 1;
      } else {forecast =0;};
      
      if(y[i][0] == 1)
      {
        if(forecast==1)
        {
          exPostTable[0]+=1;
        } else
        {
          exPostTable[1]+=1;
        }
      } else
      {
        if(forecast==1)
        {
          exPostTable[2]+=1;
        } else
        {
          exPostTable[3]+=1;
        }
      }
    }
    
    R_2=((double)exPostTable[0]+(double)exPostTable[3])/(double)y.length;
    return exPostTable;
    
  }
  
  public double getR_2()
  {
    if(R_2==-1){
      getExPostTable();
    }
    return R_2;
  }
  

}
