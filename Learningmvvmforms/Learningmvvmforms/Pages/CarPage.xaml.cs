using Learningmvvmforms.ViewModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace Learningmvvmforms.Pages
{
	[XamlCompilation(XamlCompilationOptions.Compile)]
	public partial class CarPage : ContentPage
	{

        CarViewModel carViewModel;
		public CarPage ()
		{
            carViewModel = new CarViewModel();
            BindingContext = carViewModel;
			InitializeComponent ();
		}
	}
}