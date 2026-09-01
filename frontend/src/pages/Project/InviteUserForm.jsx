import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormMessage,
} from "@/components/ui/form";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { useState } from "react";
import { useDispatch } from "react-redux";
import { inviteToProject } from "@/redux/Project/Project.Action";
import { Loader2, CheckCircle2, XCircle } from "lucide-react";

const formSchema = z.object({
  email: z.string().email("Invalid email address"),
});

const InviteUserForm = ({ projectId }) => {
  const dispatch = useDispatch();
  const [status, setStatus] = useState("idle");
  const [errorMsg, setErrorMsg] = useState("");

  const form = useForm({
    resolver: zodResolver(formSchema),
    defaultValues: {
      email: "",
    },
  });

  const onSubmit = async (data) => {
    setStatus("loading");
    setErrorMsg("");
    try {
      await dispatch(inviteToProject({ ...data, projectId }));
      setStatus("success");
      form.reset(); // clear the input on success
    } catch (error) {
      setStatus("error");
      setErrorMsg(
          error?.response?.data?.message || "Failed to send invitation. Please try again."
      );
    }
  };

  return (
      <div>
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
            <FormField
                control={form.control}
                name="email"
                render={({ field }) => (
                    <FormItem>
                      <FormControl>
                        <Input
                            {...field}
                            className="border w-full border-gray-700 py-5 px-5"
                            placeholder="enter user email"
                            disabled={status === "loading"}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                )}
            />

            <Button
                type="submit"
                className="w-full bg-slate-400 py-5"
                disabled={status === "loading"}
            >
              {status === "loading" ? (
                  <span className="flex items-center justify-center gap-2">
                <Loader2 className="h-4 w-4 animate-spin" />
                Sending...
              </span>
              ) : (
                  "SEND INVITATION"
              )}
            </Button>

            {status === "success" && (
                <div className="flex items-center gap-2 text-green-600 text-sm">
                  <CheckCircle2 className="h-4 w-4" />
                  Invitation sent successfully!
                </div>
            )}

            {status === "error" && (
                <div className="flex items-center gap-2 text-red-600 text-sm">
                  <XCircle className="h-4 w-4" />
                  {errorMsg}
                </div>
            )}
          </form>
        </Form>
      </div>
  );
};

export default InviteUserForm;