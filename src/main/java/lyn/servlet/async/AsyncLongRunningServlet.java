package lyn.servlet.async;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/AsyncLongRunningServlet", asyncSupported = true)
public class AsyncLongRunningServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		long startTime = System.currentTimeMillis();
		System.out.println("AsyncLongRunningServlet Start::Name=" + Thread.currentThread().getName() + "::ID="
				+ Thread.currentThread().getId());

		request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);

		String time = request.getParameter("time");
		int secs = Integer.valueOf(time);
		// max 10 seconds
		if (secs > 10000)
			secs = 10000;

		AsyncContext asyncCtx = request.startAsync();
		asyncCtx.addListener(new AppAsyncListener());
		asyncCtx.setTimeout(9000);

		ThreadPoolExecutor executor = (ThreadPoolExecutor) request.getServletContext().getAttribute("executor");
		 
		
		//ExecutorService executor = Executors.newSingleThreadExecutor();
		FutureTask<Object> future = new FutureTask<>(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				int secs = Integer.valueOf(time);
				System.out.println("Async Supported? " + asyncCtx.getRequest().isAsyncSupported());
				try {
					Thread.sleep(secs);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				try {
					System.out.println("睡醒了");
					PrintWriter out = asyncCtx.getResponse().getWriter();
					out.write("Processing done for " + secs + " milliseconds!!");
				} catch (IOException e) {
					e.printStackTrace();
				}

				System.out.println("running over");

				// complete the processing
				asyncCtx.complete();
				return "";
			}

		});

		executor.execute(future);
		try {
			future.get(9000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long endTime = System.currentTimeMillis();
		System.out.println("AsyncLongRunningServlet End::Name=" + Thread.currentThread().getName() + "::ID="
				+ Thread.currentThread().getId() + "::Time Taken=" + (endTime - startTime) + " ms.");
	}

}