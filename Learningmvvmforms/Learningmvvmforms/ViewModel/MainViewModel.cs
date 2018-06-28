using Learningmvvmforms.Common;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Text;
using Xamarin.Forms;

namespace Learningmvvmforms.ViewModel
{
    public class MainViewModel:INotifyPropertyChanged
    {

        public MainViewModel()
        {
            SayHelloCommand = new DelegateCommand(ExecuteHello);
        }

        private void ExecuteHello(object obj)
        {
            HelloMessage = "Hello" + Name;
        }

        private string _name;
        public DelegateCommand SayHelloCommand { get; set; }
        public string  HelloMessage {get
            {
                return _name;
            }
            set
            {
                _name = value;
                OnPropertyChanged();
            }
          }
        public string Name { get; set; }
        //public Command SayHelloCommand
        //{
        //    get
        //    {
        //        return new Command(() =>
        //        {
        //            HelloMessage = "Hello" + Name;
        //        });
        //    }
        //}

        public event PropertyChangedEventHandler PropertyChanged;
        protected virtual void OnPropertyChanged([CallerMemberName]string propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs((propertyName)));
        }

    }
}
