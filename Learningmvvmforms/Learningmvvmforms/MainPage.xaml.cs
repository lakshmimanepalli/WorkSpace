using Learningmvvmforms.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace Learningmvvmforms
{
	public partial class MainPage : ContentPage
	{
        MainViewModel mainViewModel;
		public MainPage()
		{
			InitializeComponent();
            mainViewModel = new MainViewModel();
            BindingContext = mainViewModel;
		}
	}
}
